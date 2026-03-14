package com.company.projectmanagement.controller;

import com.company.projectmanagement.dto.TaskRequestDTO;
import com.company.projectmanagement.dto.TaskResponseDTO;
import com.company.projectmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO requestDTO){
        return taskService.createTask(requestDTO);
    }

    @GetMapping("/project/{projectId}")
    public Page<TaskResponseDTO> getTasksByProject(@PathVariable Long projectId, Pageable pageable){
        return taskService.getTasksByProject(projectId, pageable);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
    }

    @PutMapping("/{taskId}")
    public TaskResponseDTO updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequestDTO requestDTO){
        return taskService.updateTask(taskId, requestDTO);
    }
    @GetMapping("/project/{projectId}/filter")
    public Page<TaskResponseDTO> getTasksByProjectAndStatus(@PathVariable Long projectId, @RequestParam String status, Pageable pageable){
        return taskService.getTasksByProjectAndStatus(projectId, status, pageable);
    }

}