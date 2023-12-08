package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeTaskDto;
import com.example.trabalhoDevWeb.models.TypeTask;
import com.example.trabalhoDevWeb.services.TypeTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/typeTask")
public class TypeTaskController {
    private final TypeTaskService typeTaskService;

    @Autowired
    public TypeTaskController(TypeTaskService typeTaskService) {
        this.typeTaskService = typeTaskService;
    }

    @PostMapping
    public ResponseEntity<TypeTask> saveTypeTask(@RequestBody @Valid TypeTaskDto typeTaskDto) {
        TypeTask savedTypeTask = typeTaskService.saveTypeTask(typeTaskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTypeTask);
    }

    @GetMapping
    public ResponseEntity<List<TypeTask>> getAllTypeTasks() {
        List<TypeTask> allTypeTasks = typeTaskService.getAllTypeTasks();
        return ResponseEntity.status(HttpStatus.OK).body(allTypeTasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTypeTask(@PathVariable(value = "id") long id) {
        return typeTaskService.getOneTypeTask(id)
                .map(typeTask -> ResponseEntity.status(HttpStatus.OK).body((Object) typeTask))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTypeTask(@PathVariable(value = "id") long id,
                                                 @RequestBody @Valid TypeTaskDto typeTaskDto) {
        return typeTaskService.updateTypeTask(id, typeTaskDto)
                .map(updatedTypeTask -> ResponseEntity.status(HttpStatus.CREATED).body((Object) updatedTypeTask))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTypeTask(@PathVariable(value = "id") long id) {
        boolean isDeleted = typeTaskService.deleteTypeTask(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Type task deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found");
    }
}

