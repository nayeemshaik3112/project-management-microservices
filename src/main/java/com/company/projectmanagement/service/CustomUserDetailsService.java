package com.company.projectmanagement.service;

import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //Converts this user into USER details object , because spring secuirty cannot understand our USER entity

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new java.util.ArrayList<>()
        );
    }
}