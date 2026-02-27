package com.company.projectmanagement.service;
import com.company.projectmanagement.dto.AuthRequest;
import com.company.projectmanagement.dto.AuthResponse;
import com.company.projectmanagement.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}
