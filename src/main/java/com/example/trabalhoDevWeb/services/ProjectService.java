package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.ProjectDto;
import com.example.trabalhoDevWeb.models.Project;
import com.example.trabalhoDevWeb.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.name());
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getOneProject(long id) {
        return projectRepository.findById(id);
    }

    public Optional<Project> updateProject(long id, ProjectDto projectDto) {
        Optional<Project> projectSelected = projectRepository.findById(id);

        if (projectSelected.isPresent()) {
            Project project = projectSelected.get();
            project.setName(projectDto.name());
            return Optional.of(projectRepository.save(project));
        }

        return Optional.empty();
    }

    public boolean deleteProject(long id) {
        Optional<Project> projectSelected = projectRepository.findById(id);

        if (projectSelected.isPresent()) {
            projectRepository.delete(projectSelected.get());
            return true;
        }

        return false;
    }
}

