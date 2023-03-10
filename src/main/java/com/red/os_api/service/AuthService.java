package com.red.os_api.service;

import com.red.os_api.entity.*;
import com.red.os_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthentificationResponse register(RegisterRequest request) {
       var auth = Auth.builder().username(request.getEmail()).role(Role.USER).password(passwordEncoder.encode(request.getPassword()))
                       .build();
        var saved = repository.save(auth);
       var token = jwtService.generateToken(auth);
       return AuthentificationResponse.builder().token(token).build();

    }

    public AuthentificationResponse authenticate(AuthentificationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var auth = repository.findByUsername(request.getEmail())
                .orElseThrow();
        var Token = jwtService.generateToken(auth);
        return AuthentificationResponse.builder()
                .token(Token)
                .build();

    }
}
