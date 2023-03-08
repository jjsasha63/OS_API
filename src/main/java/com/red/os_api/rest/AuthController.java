package com.red.os_api.rest;

import com.red.os_api.entity.AuthentificationRequest;
import com.red.os_api.entity.AuthentificationResponse;
import com.red.os_api.entity.RegisterRequest;
import com.red.os_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store/auth")
public class AuthController {

    @Autowired
   private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthentificationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("/auth")
    public ResponseEntity<AuthentificationResponse> register(@RequestBody AuthentificationRequest request){
        return ResponseEntity.ok(authService.authentificate(request));
    }

}
