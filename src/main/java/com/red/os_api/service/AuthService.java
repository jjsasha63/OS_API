package com.red.os_api.service;


import com.red.os_api.encryption.AES;
import com.red.os_api.entity.Auth;
import com.red.os_api.entity.req_resp.AuthRequest;
import com.red.os_api.entity.req_resp.AuthResponse;
import com.red.os_api.entity.req_resp.MasterResponse;
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
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

  @Value("${cid.auth.key}")
  private final String SECRET;

  private final AuthRepository authRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder encoder;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  @Value("${master.key}")
  private final String ADM;

  @Value("${master.key.su}")
  private final String MST;
   private static String KEY;

  private static String PHRASE;

  public AuthResponse auth(AuthRequest authRequest) throws AccessDeniedException {
    if(authRepository.findAuthByEmail(authRequest.getEmail()).get().getRole()==Role.MASTER){
      KEY=null;
      PHRASE=null;
      throw new AccessDeniedException("Access denied");
    }
    var user = authRepository.findAuthByEmail(authRequest.getEmail()).orElseThrow();
    authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword(),user.getAuthorities()));
    var jwtToken = jwtService.generateToken(user);
    revokeTokens(user);
    saveToken(user, jwtToken);
    return AuthResponse.builder().token(jwtToken).role(user.getRole().name()).build();
  }

  public AuthResponse authMaster(AuthRequest authRequest) {
    String dec = AES.decrypt(authRequest.getToken(),KEY);
    if(PHRASE.equals(dec)&&authRepository.existsByIdAndRoleIs(
            authRepository.findAuthByEmail(
                    authRequest.getEmail()).get().getId(),Role.MASTER)) {
      KEY = null;
      PHRASE =null;
      var user = authRepository.findAuthByEmail(authRequest.getEmail()).orElseThrow();
      authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword(),user.getAuthorities()));
      var jwtToken = jwtService.generateToken(user);
      revokeTokens(user);
      saveToken(user, jwtToken);
      return AuthResponse.builder().token(jwtToken).role(user.getRole().name()).build();
    } else{
      KEY = null;
      PHRASE =null;
      throw new RuntimeException();
    }
  }

  public ResponseEntity<MasterResponse> insert(RegisterRequest registerRequest) {
    var auth = new Auth();
    try {
      if (registerRequest.getAuth_id() != null && authRepository.existsById(registerRequest.getAuth_id()))
        auth = authRepository.findById(registerRequest.getAuth_id()).get();
      if (registerRequest.getEmail() != null) auth.setEmail(registerRequest.getEmail());
      if (registerRequest.getFirst_name() != null)
        auth.setFirst_name(AES.encrypt(registerRequest.getFirst_name(), SECRET));
      if (registerRequest.getLast_name() != null)
        auth.setLast_name(AES.encrypt(registerRequest.getLast_name(), SECRET));
      if (registerRequest.getPassword() != null) auth.setPassword(encoder.encode(registerRequest.getPassword()));
      if (registerRequest.getRole() != null) auth.setRole(Role.valueOf(registerRequest.getRole()));
      var saved = authRepository.save(auth);
      var token = jwtService.generateToken(auth);
      saveToken(saved, token);
    } catch (Exception e){
      log.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(toResponse(auth),HttpStatus.OK);
  }

  private MasterResponse toResponse(Auth auth){
    MasterResponse masterResponse = new MasterResponse();
    masterResponse.setAuth_id(auth.getId());
    masterResponse.setRole(auth.getRole().name());
    masterResponse.setFirst_name(AES.decrypt(auth.getFirst_name(),SECRET));
    masterResponse.setLast_name(AES.decrypt(auth.getLast_name(),SECRET));
    masterResponse.setEmail(auth.getEmail());
    return masterResponse;

  }

  private List<MasterResponse> toResponse(List<Auth> authList){
    List<MasterResponse> masterResponseList = new ArrayList<>();
    for(Auth auth:authList) {
      MasterResponse masterResponse = new MasterResponse();
      masterResponse.setAuth_id(auth.getId());
      masterResponse.setRole(auth.getRole().name());
      masterResponse.setFirst_name(AES.decrypt(auth.getFirst_name(),SECRET));
      masterResponse.setLast_name(AES.decrypt(auth.getLast_name(),SECRET));
      masterResponse.setEmail(auth.getEmail());
      masterResponseList.add(masterResponse);
    }
    return masterResponseList;

  }

  public ResponseEntity<List<MasterResponse>> getAll(){
    List<Auth> authList = new ArrayList<>();
    try{
      authList = authRepository.findAll();
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(toResponse(authList),HttpStatus.OK);
  }

  public ResponseEntity<String> deleteById(Integer id){
    try{
      authRepository.deleteById(id);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public AuthResponse register(RegisterRequest registerRequest) {
    var auth = Auth.builder().email(registerRequest.getEmail())
        .first_name(AES.encrypt(registerRequest.getFirst_name(),SECRET))
        .last_name(AES.encrypt(registerRequest.getLast_name(),SECRET))
        .password(encoder.encode(registerRequest.getPassword()))
        .role(Role.CUSTOMER)
        .build();
    var saved = authRepository.save(auth);
    var token = jwtService.generateToken(auth);
    saveToken(saved, token);
    return AuthResponse.builder().token(token).build();
  }

  public AuthResponse register(RegisterRequest registerRequest,@NonNull HttpServletRequest request,
                               @NonNull HttpServletResponse response,
                               @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
    var auth = Auth.builder().id(getUserId(request,response,filterChain)).email(registerRequest.getEmail())
            .first_name(AES.encrypt(registerRequest.getFirst_name(),SECRET))
            .last_name(AES.encrypt(registerRequest.getLast_name(),SECRET))
            .password(encoder.encode(registerRequest.getPassword()))
            .role(Role.CUSTOMER)
            .build();
    var saved = authRepository.save(auth);
    var token = jwtService.generateToken(auth);
    saveToken(saved, token);
    return AuthResponse.builder().token(token).build();
  }

  public AuthResponse registerAdmin(RegisterRequest registerRequest) throws AccessDeniedException {
    if(registerRequest.getToken()!=null&&(registerRequest.getToken().equals(ADM)||registerRequest.getToken().equals(MST))){
      Role role = Role.ADMIN;
      if(registerRequest.getToken().equals(MST)) role = Role.MASTER;
    var auth = Auth.builder().email(registerRequest.getEmail())
            .first_name(AES.encrypt(registerRequest.getFirst_name(),SECRET))
            .last_name(AES.encrypt(registerRequest.getLast_name(),SECRET))
            .password(encoder.encode(registerRequest.getPassword()))
            .role(role)
            .build();
    var saved = authRepository.save(auth);
    var token = jwtService.generateToken(auth);
    saveToken(saved, token);
    return AuthResponse.builder().token(token).role(role.name()).build();
    } else throw new AccessDeniedException("The token is invalid");
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
    Integer id = getUserId(request,response,filterChain);
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
    String temp = authHeader.replaceAll("Bearer \\{\"token\":\"(.*?)\"\\}", "Bearer $1");
    if(!tokenRepository.existsTokenByTokenAndRevokedAndExpired(temp
            .substring(7),false,false)) throw new NoSuchFieldException();

    return authRepository.findAuthByEmail(jwtService
            .getUsername(temp
                    .substring(7))).get().getId();
  }

  public AuthResponse generateEncrypted(AuthRequest authRequest){
    if(authRequest.getToken().equals(ADM)) {
      PHRASE = RandomString.make(99);
      KEY = RandomString.make(512);
      return new AuthResponse(AES.encrypt(PHRASE, KEY),"");
    }
    return new AuthResponse("Invalid token","");
  }

}
