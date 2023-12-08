package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.ProjectDto;
import com.example.trabalhoDevWeb.models.Project;
import com.example.trabalhoDevWeb.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @PostMapping
    public ResponseEntity<Object> saveProject(@RequestBody @Valid ProjectDto projectDto) {
        Project savedProject = projectService.saveProject(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> allProjects = projectService.getAllProjects();
        return ResponseEntity.status(HttpStatus.OK).body(allProjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProject(@PathVariable(value = "id") long id) {
        return projectService.getOneProject(id)
                .map(project -> ResponseEntity.status(HttpStatus.OK).body((Object) project))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable(value = "id") long id,
                                                @RequestBody @Valid ProjectDto projectDto) {
        return projectService.updateProject(id, projectDto)
                .map(updatedProject -> ResponseEntity.status(HttpStatus.CREATED).body((Object) updatedProject))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable(value = "id") long id) {
        boolean isDeleted = projectService.deleteProject(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Project deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
    }
}
