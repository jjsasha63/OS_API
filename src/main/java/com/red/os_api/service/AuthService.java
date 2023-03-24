package com.red.os_api.service;


import com.red.os_api.entity.Auth;
import com.red.os_api.entity.req_resp.AuthRequest;
import com.red.os_api.entity.req_resp.AuthResponse;
import com.red.os_api.entity.req_resp.RegisterRequest;
import com.red.os_api.entity.Token;
import com.red.os_api.repository.AuthRepository;
import com.red.os_api.repository.TokenRepository;
import com.red.os_api.entity.TokenType;
import com.red.os_api.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthRepository authRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder encoder;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;


  public AuthResponse auth(AuthRequest authRequest) {
    authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
    var user = authRepository.findByEmail(authRequest.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeTokens(user);
    saveToken(user, jwtToken);
    return AuthResponse.builder().token(jwtToken).build();
  }


  public AuthResponse register(RegisterRequest registerRequest) {
    var auth = Auth.builder().email(registerRequest.getEmail())
        .first_name(registerRequest.getFirst_name())
        .last_name(registerRequest.getLast_name())
        .password(encoder.encode(registerRequest.getPassword()))
        .role(Role.CUSTOMER)
        .build();
    var saved = authRepository.save(auth);
    var token = jwtService.generateToken(auth);
    saveToken(saved, token);
    return AuthResponse.builder().token(token).build();
  }

  private void revokeTokens(Auth auth) {
    var tokens = tokenRepository.findAllValidTokenByUser(auth.getId());
    if (tokens.isEmpty()) return;
    tokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);});
    tokenRepository.saveAll(tokens);
  }

  private void saveToken(Auth auth, String jwtToken) {
    var token = Token.builder().auth(auth).token(jwtToken)
            .token_type(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

}
