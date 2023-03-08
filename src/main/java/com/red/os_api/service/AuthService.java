package com.red.os_api.service;

import com.red.os_api.entity.Auth;
import com.red.os_api.entity.AuthentificationRequest;
import com.red.os_api.entity.AuthentificationResponse;
import com.red.os_api.entity.RegisterRequest;
import com.red.os_api.repository.UserRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    public AuthentificationResponse register(RegisterRequest request) {
       //TODO var user = .username(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
             //   .roles("user").build();
        repository.save(user);
    }

    public AuthentificationResponse authentificate(AuthentificationRequest request) {
    }
}
