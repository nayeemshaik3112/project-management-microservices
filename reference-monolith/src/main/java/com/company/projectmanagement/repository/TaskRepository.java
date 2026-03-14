package com.company.projectmanagement.repository;

import com.company.projectmanagement.entity.Task;
import com.company.projectmanagement.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByProject(Project project, Pageable pageable);
    Page<Task> findByProjectAndStatus(Project project, String status, Pageable pageable);
}