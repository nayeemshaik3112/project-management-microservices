package com.company.projectmanagement.controller;

import com.company.projectmanagement.dto.AuthRequest;
import com.company.projectmanagement.dto.AuthResponse;
import com.company.projectmanagement.dto.RegisterRequest;
import com.company.projectmanagement.repository.UserRepository;
import com.company.projectmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        AuthResponse response = authService.register(request);
        return response;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        AuthResponse reponse = authService.login(request);
        return reponse;
    }
}
