package com.red.os_api.rest;

import com.red.os_api.entity.req_resp.AuthRequest;
import com.red.os_api.entity.req_resp.AuthResponse;
import com.red.os_api.service.AuthService;
import com.red.os_api.entity.req_resp.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
    return ResponseEntity.ok(authService.auth(authRequest));
  }


}
