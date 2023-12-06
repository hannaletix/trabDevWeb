package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
