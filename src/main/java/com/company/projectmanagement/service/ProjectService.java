package com.company.projectmanagement.service;

import com.company.projectmanagement.dto.ProjectRequestDTO;
import com.company.projectmanagement.dto.ProjectResponseDTO;
import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.User;
import com.company.projectmanagement.repository.ProjectRepository;
import com.company.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


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
    public List<ProjectResponseDTO> getMyProjects(){
        User user = getAuthentication();
        List<Project> projects = projectRepository.findByUser(user);
        return projects.stream().map(this::mapToDTO).toList();
    }
    public ProjectResponseDTO findById(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found with ID " + id));
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

