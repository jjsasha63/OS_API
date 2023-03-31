package com.red.os_api.service;


import com.red.os_api.encryption.AES;
import com.red.os_api.entity.Auth;
import com.red.os_api.entity.req_resp.AuthRequest;
import com.red.os_api.entity.req_resp.AuthResponse;
import com.red.os_api.entity.req_resp.RegisterRequest;
import com.red.os_api.entity.Token;
import com.red.os_api.repository.AuthRepository;
import com.red.os_api.repository.TokenRepository;
import com.red.os_api.entity.TokenType;
import com.red.os_api.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthRepository authRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder encoder;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  @Value("${master.key}")
  private final String T;
   private static String KEY;

  private static String PHRASE;

  public AuthResponse auth(AuthRequest authRequest) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
    authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
    var user = authRepository.findByEmail(authRequest.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeTokens(user);
    saveToken(user, jwtToken);
    return AuthResponse.builder().token(jwtToken).build();
  }

  public AuthResponse authMaster(AuthRequest authRequest) {
    String dec = AES.decrypt(authRequest.getToken(),KEY);
    if(PHRASE.equals(dec)) {
      KEY = null;
      PHRASE =null;
      authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
      var user = authRepository.findByEmail(authRequest.getEmail()).orElseThrow();
      var jwtToken = jwtService.generateToken(user);
      revokeTokens(user);
      saveToken(user, jwtToken);
      return AuthResponse.builder().token(jwtToken).build();
    } else throw new RuntimeException();
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

  public AuthResponse registerAdmin(RegisterRequest registerRequest) {
    var auth = Auth.builder().email(registerRequest.getEmail())
            .first_name(registerRequest.getFirst_name())
            .last_name(registerRequest.getLast_name())
            .password(encoder.encode(registerRequest.getPassword()))
            .role(Role.ADMIN)
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

  public ResponseEntity<String> logout(@NonNull HttpServletRequest request,
                                       @NonNull HttpServletResponse response,
                                       @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
    Auth auth = authRepository.findById(getUserId(request,response,filterChain)).get();
    revokeTokens(auth);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  Integer getUserId(@NonNull HttpServletRequest request,
                    @NonNull HttpServletResponse response,
                    @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      throw new IllegalArgumentException();
    }
    if(!tokenRepository.existsTokenByTokenAndRevokedAndExpired(authHeader
            .substring(7),false,false)) throw new NoSuchFieldException();

    return authRepository.findByEmail(jwtService
            .getUsername(authHeader
                    .substring(7))).get().getId();
  }

  public AuthResponse generateEncrypted(AuthRequest authRequest){
    if(authRequest.getToken().equals(T)) {
      PHRASE = RandomString.make(99);
      KEY = RandomString.make(512);
      return new AuthResponse(AES.encrypt(PHRASE, KEY));
    }
    return new AuthResponse("Invalid token");
  }

}
