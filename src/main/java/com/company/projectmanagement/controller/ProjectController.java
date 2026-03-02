package com.company.projectmanagement.controller;

import com.company.projectmanagement.dto.ProjectRequestDTO;
import com.company.projectmanagement.dto.ProjectResponseDTO;
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
    @PostMapping("/create")
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO request){
        ProjectResponseDTO dto = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // GET ALL PROJECTS (USER and ADMIN)
    @GetMapping("/my-projects")
    public ResponseEntity<List<ProjectResponseDTO>> getMyProjects() {
        List<ProjectResponseDTO> dto = projectService.getMyProjects();
        return ResponseEntity.ok(dto);
    }

    // GET PROJECT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        ProjectResponseDTO DTO  = projectService.findById(id);
        return ResponseEntity.ok(DTO);
    }

    // DELETE PROJECT (ADMIN only)
    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteProject(@PathVariable Long id) {
        projectService.DeleteProject(id);
        return ResponseEntity.ok("Project Deleted Successfully...");
    }
}