package com.company.projectmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDTO {
    @NotBlank(message = "Task title cannot be empty")
    @Size(min = 3, max = 100, message = "Task title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Task description cannot be empty")
    @Size(min = 5, max = 500, message = "Task description must be between 5 and 500 characters")
    private String description;

    @NotBlank(message = "Task status cannot be empty")
    private String status;

    @NotNull(message = "Project ID cannot be null")
    private Long projectId;

}