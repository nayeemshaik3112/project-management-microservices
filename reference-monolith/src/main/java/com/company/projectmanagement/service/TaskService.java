package com.company.projectmanagement.service;

import com.company.projectmanagement.dto.TaskRequestDTO;
import com.company.projectmanagement.dto.TaskResponseDTO;
import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.Task;
import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.exception.ProjectNotFoundException;
import com.company.projectmanagement.exception.ResourceNotFoundException;
import com.company.projectmanagement.repository.ProjectRepository;
import com.company.projectmanagement.repository.TaskRepository;
import com.company.projectmanagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskResponseDTO createTask(TaskRequestDTO requestDTO){

        User user = getAuthenticatedUser();

        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() ->
                        new ProjectNotFoundException("Project not found with id: "
                                + requestDTO.getProjectId()));

        if(!project.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not authorized to add task to this project");
        }

        Task task = Task.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .status(requestDTO.getStatus())
                .project(project)
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToDTO(savedTask);
    }

    public Page<TaskResponseDTO> getTasksByProject(Long projectId, Pageable pageable){

        User user = getAuthenticatedUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectNotFoundException("Project not found with id: "
                                + projectId));

        if(!project.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not authorized to view tasks of this project");
        }

        Page<Task> taskPage = taskRepository.findByProject(project, pageable);

        return taskPage.map(this::mapToDTO);
    }

    public void deleteTask(Long taskId){

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found with id: " + taskId));

        taskRepository.delete(task);
    }

    private TaskResponseDTO mapToDTO(Task task){

        return TaskResponseDTO.
                builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .build();
    }

    private User getAuthenticatedUser(){

        String username =
                org.springframework.security.core.context.SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO requestDTO){
        User user = getAuthenticatedUser();
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                "Project not found with id: "
                                        + requestDTO.getProjectId()));

        if(!project.getUser().getId().equals(user.getId())){
            //Any Exeception we can use
            throw new ResourceNotFoundException(
                    "You are not authorized to update this task");
        }

        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setStatus(requestDTO.getStatus());
        task.setProject(project);
        Task updatedTask = taskRepository.save(task);

        return mapToDTO(updatedTask);
    }

    public Page<TaskResponseDTO> getTasksByProjectAndStatus(Long projectId, String status, Pageable pageable){

        User user = getAuthenticatedUser();
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));

        if(!project.getUser().getId().equals(user.getId())){
            throw new ResourceNotFoundException("You are not authorized to view tasks of this project");
        }

        Page<Task> taskPage = taskRepository.findByProjectAndStatus(project, status, pageable);
        return taskPage.map(this::mapToDTO);
    }
}