package com.company.projectmanagement.service;

import com.company.projectmanagement.dto.ProjectRequest;
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

    public Project createProject(ProjectRequest request){
        User user = getAuthentication();
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        project.setUser(user);
        project.setCreatedAt(LocalDateTime.now());
        return projectRepository.save(project);
    }
    public List<Project> getMyProjects() {
        User user = getAuthentication();
        return projectRepository.findByUser(user);
    }
    public Project findById(Long id) {
        return projectRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Project not found with this ID" + id));
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
}

