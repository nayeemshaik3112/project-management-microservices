package com.company.projectmanagement.dto;


import lombok.Getter;

@Getter
public class ProjectRequest {

        private String name;
        private String description;
        private String status;

    public ProjectRequest(String name, String status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

