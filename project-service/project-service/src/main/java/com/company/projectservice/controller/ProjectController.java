package com.company.projectservice.controller;

import com.company.projectservice.DTO.UserDTO;
import com.company.projectservice.client.UserClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final UserClient userClient;

    public ProjectController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/{username}")
    public String getProjectForUser(@PathVariable String username) {

        UserDTO user = userClient.getUser(username);

        return "Project assigned to user: " + user.getUsername();
    }
}