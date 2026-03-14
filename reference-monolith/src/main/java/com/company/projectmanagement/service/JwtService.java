package com.company.projectmanagement.service;

import com.company.projectmanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private JwtUtil jwtUtil;

    public String generateToken(String username) {

        return jwtUtil.generateToken(username);
    }

}
