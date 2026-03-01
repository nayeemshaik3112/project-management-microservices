package com.company.projectmanagement.service;

import com.company.projectmanagement.dto.AuthRequest;
import com.company.projectmanagement.dto.AuthResponse;
import com.company.projectmanagement.dto.RegisterRequest;
import com.company.projectmanagement.entity.Role;
import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.repository.RoleRepository;
import com.company.projectmanagement.repository.UserRepository;
import com.company.projectmanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request){

        if (userRepository.findByUsername(request.getUsername()).isPresent())
                throw new RuntimeException("User Already Present");

        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("role not found"));
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(role);
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse (token);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }
}
