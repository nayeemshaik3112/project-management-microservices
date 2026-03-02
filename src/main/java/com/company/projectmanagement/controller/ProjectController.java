package com.company.projectmanagement.controller;

import com.company.projectmanagement.dto.ProjectRequest;
import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // CREATE PROJECT (ADMIN only - enforced by SecurityConfig)
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request) {
        Project project = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(project);
    }

    // GET ALL PROJECTS (USER and ADMIN)
    @GetMapping("/my-projects")
    public ResponseEntity<List<Project>> getMyProjects() {
        List<Project> projects = projectService.getMyProjects();
        return ResponseEntity.ok(projects);
    }

    // GET PROJECT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project  = projectService.findById(id);
        return ResponseEntity.ok(project);
    }

    // DELETE PROJECT (ADMIN only)
    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteProject(@PathVariable Long id) {
        projectService.DeleteProject(id);
        return ResponseEntity.ok("Project Deleted Successfully...");
    }
}