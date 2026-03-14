package com.company.projectmanagement.controller;

import com.company.projectmanagement.dto.ProjectRequestDTO;
import com.company.projectmanagement.dto.ProjectResponseDTO;
import com.company.projectmanagement.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO request){
        ProjectResponseDTO dto = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/my")
    public Page<ProjectResponseDTO> getMyProjects(Pageable pageable) {
        return projectService.getMyProjectsWithPagination(pageable);
    }

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
    //Update the Project
    @PutMapping("/{projectId}")
    public ProjectResponseDTO updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectRequestDTO requestDTO){
        return projectService.updateProject(projectId, requestDTO);
    }
}