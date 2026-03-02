package com.company.projectmanagement.controller;

import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.repository.UserRepository;
import com.company.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    // CREATE PROJECT (ADMIN only - enforced by SecurityConfig)
    @PostMapping("/create")
    public Project createProjec(@RequestBody Project project, Authentication authentication) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return projectService.createProject(project, user);
    }

    // GET ALL PROJECTS (USER and ADMIN)
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // GET PROJECT BY ID
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    // DELETE PROJECT (ADMIN only)
    @DeleteMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.DeleteProject(id);
        return "Project deleted successfully";
    }
}
// Doing one small change to see git is working or not