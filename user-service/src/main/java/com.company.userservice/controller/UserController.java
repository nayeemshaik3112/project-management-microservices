package com.company.userservice.controller;

import com.company.userservice.entity.Role;
import com.company.userservice.entity.User;
import com.company.userservice.repository.RoleRepository;
import com.company.userservice.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        Role role = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow();
        user.setRole(role);
        return userRepository.save(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) throws InterruptedException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}