package com.company.projectservice.controller;

import com.company.projectservice.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{username}")
    public CompletableFuture<String> getProjectForUser(@PathVariable String username) {

        return projectService.getProjectForUser(username);
    }
}