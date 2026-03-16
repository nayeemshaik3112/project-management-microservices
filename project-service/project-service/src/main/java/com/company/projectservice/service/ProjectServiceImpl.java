package com.company.projectservice.service;

import com.company.projectservice.DTO.UserDTO;
import com.company.projectservice.client.UserClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final UserClient userClient;

    public ProjectServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "userServiceFallback")//Protect this method with circuit breaker
    public String getProjectForUser(String username) {
        UserDTO user = userClient.getUser(username);
        return "Project assigned to user: " + user.getUsername();
    }

    public String userServiceFallback(String username, Throwable throwable) {
        return "User Service is currently unavailable. Please try later.";
    }
}