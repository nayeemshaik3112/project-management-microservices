package com.company.projectservice.service;

import com.company.projectservice.DTO.UserDTO;
import com.company.projectservice.client.UserClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final UserClient userClient;

    public ProjectServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    @Retry(name = "userServiceRetry")
    @CircuitBreaker(name = "userService", fallbackMethod = "userServiceFallback")
    @TimeLimiter(name = "userServiceTimeout")
    @Bulkhead(name = "userServiceBulkhead")
    @RateLimiter(name = "userServiceRateLimiter")
    public CompletableFuture<String> getProjectForUser(String username) {

        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Calling User Service...");
            UserDTO user = userClient.getUser(username);
            return "Project assigned to user: " + user.getUsername();
        });
    }
    public CompletableFuture<String> userServiceFallback(String username, Throwable throwable) {

        System.out.println("Fallback triggered because of: " + throwable.getClass().getName());
        return CompletableFuture.completedFuture(
                "Fallback due to: " + throwable.getClass().getSimpleName()
        );
    }
}