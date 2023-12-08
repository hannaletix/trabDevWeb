package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TaskDto;
import com.example.trabalhoDevWeb.models.Task;
import com.example.trabalhoDevWeb.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody @Valid TaskDto taskDto) {
        Task savedTask = taskService.saveTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();
        return ResponseEntity.status(HttpStatus.OK).body(allTasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id") long id) {
        return taskService.getOneTask(id)
                .map(task -> ResponseEntity.status(HttpStatus.OK).body((Object) task))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") long id,
                                             @RequestBody @Valid TaskDto taskDto) {
        return taskService.updateTask(id, taskDto)
                .map(updatedTask -> ResponseEntity.status(HttpStatus.CREATED).body((Object) updatedTask))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") long id) {
        boolean isDeleted = taskService.deleteTask(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }
}

