package com.red.os_api.rest;

import com.red.os_api.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/store/api/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final AuthService authService;

    @DeleteMapping
    public ResponseEntity<String> logout(@NonNull HttpServletRequest request,
                                         @NonNull HttpServletResponse response,
                                         @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
        return authService.logout(request,response,filterChain);
    }
}
