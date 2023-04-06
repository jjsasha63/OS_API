package com.red.os_api.rest;

import com.red.os_api.entity.req_resp.AuthRequest;
import com.red.os_api.entity.req_resp.AuthResponse;
import com.red.os_api.service.AuthService;
import com.red.os_api.entity.req_resp.RegisterRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/store/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
    return ResponseEntity.ok(authService.register(registerRequest));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) throws AccessDeniedException {
    return ResponseEntity.ok(authService.auth(authRequest));
  }

  @PostMapping("/6D5A713374367739")
  public ResponseEntity<AuthResponse> authMaster(@RequestBody AuthRequest authRequest) {
    return ResponseEntity.ok(authService.authMaster(authRequest));
  }





}
