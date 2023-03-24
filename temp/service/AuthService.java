package com.red.os_api.service;


import com.red.os_api.entity.*;
import com.red.os_api.repository.TokenRepository;
import com.red.os_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRepository tokenRepository;


    public AuthenticationResponse register(RegisterRequest request) {
       var auth = Auth.builder().username(request.getEmail()).role(Role.USER).password(passwordEncoder.encode(request.getPassword()))
                       .build();
        var saved = repository.save(auth);
       var token = jwtService.generateToken(auth);
        saveUserToken(saved, token);
       return AuthenticationResponse.builder().token(token).build();

    }

    public AuthenticationResponse authenticate(AuthentificationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var auth = repository.findByUsername(request.getEmail())
                .orElseThrow();
        var token = jwtService.generateToken(auth);
        saveUserToken(auth, token);
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }

    private void saveUserToken(Auth user, String jwt) {
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Auth user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getAuth_id());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
