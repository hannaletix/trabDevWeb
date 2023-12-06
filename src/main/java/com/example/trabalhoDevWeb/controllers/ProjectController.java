package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.ProjectDto;
import com.example.trabalhoDevWeb.models.Project;
import com.example.trabalhoDevWeb.repositories.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @PostMapping
    public ResponseEntity<Project> saveProject(@RequestBody @Valid ProjectDto projectDto) {
        var project = new Project();
        project.setName(projectDto.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(projectRepository.save(project));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(projectRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProject(@PathVariable(value = "id") long id) {
        Optional<Project> projectSelected = projectRepository.findById(id);

        if(projectSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(projectSelected.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable(value = "id") long id,
                                                        @RequestBody @Valid ProjectDto projectDto) {
        Optional<Project> projectSelected = projectRepository.findById(id);

        if(projectSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found");
        }

        var project = projectSelected.get();
        BeanUtils.copyProperties(projectDto, project);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectRepository.save(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable(value = "id") long id) {
        Optional<Project> projectSelected = projectRepository.findById(id);

        if(projectSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found");
        }

        projectRepository.delete(projectSelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("Type User History deleted sucessfully");
    }
}
