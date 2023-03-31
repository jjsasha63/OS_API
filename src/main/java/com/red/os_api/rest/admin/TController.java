package com.red.os_api.rest.admin;

import com.red.os_api.entity.req_resp.AuthRequest;
import com.red.os_api.entity.req_resp.AuthResponse;
import com.red.os_api.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/store/api/admin")
@RequiredArgsConstructor
@PreAuthorize(value = "ADMIN")
public class TController {

    private final AuthService authService;


    @GetMapping("/38782F413F442847")
    public ResponseEntity<AuthResponse> requestToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.generateEncrypted(authRequest));
    }



}
