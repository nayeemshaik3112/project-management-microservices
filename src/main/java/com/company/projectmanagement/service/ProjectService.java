package com.company.projectmanagement.service;

import com.company.projectmanagement.dto.ProjectRequestDTO;
import com.company.projectmanagement.dto.ProjectResponseDTO;
import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.exception.ProjectNotFoundException;
import com.company.projectmanagement.repository.ProjectRepository;
import com.company.projectmanagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public ProjectResponseDTO createProject(ProjectRequestDTO request){
        User user = getAuthentication();
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        project.setUser(user);
        project.setCreatedAt(LocalDateTime.now());
        Project savedproject = projectRepository.save(project);
        return mapToDTO(savedproject);
    }
    public Page<ProjectResponseDTO> getMyProjectsWithPagination(Pageable pageable){
        User user = getAuthentication();
        Page<Project> projectPage = projectRepository.findByUser(user, pageable);
        return projectPage.map(this::mapToDTO);
    }
    public ProjectResponseDTO findById(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        return mapToDTO(project);
    }
    public void DeleteProject(Long id){
        if(!projectRepository.existsById(id)){
            throw new RuntimeException("Project not found to delete");
        }
        projectRepository.deleteById(id);
    }

    public User getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public ProjectResponseDTO updateProject(long id, ProjectRequestDTO request){

        User user = getAuthentication();
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project Not Found with this ID + " + id));
        if(!project.getUser().getId().equals(user.getId())){
            throw new RuntimeException(
                    "You are not authorized to update this project");
        }
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        Project updatedProject = projectRepository.save(project);
        return mapToDTO(updatedProject);
    }


    private ProjectResponseDTO mapToDTO(Project project){
        ProjectResponseDTO dto = ProjectResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .username(project.getUser().getUsername())
                .build();
        return dto;
    }

}

