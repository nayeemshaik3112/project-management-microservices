package com.company.projectmanagement.repository;

import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByUser(User user, Pageable pageable);

    List<Project> findByid(Long id);
}