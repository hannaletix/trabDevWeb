package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TaskDto;
import com.example.trabalhoDevWeb.dtos.UserHistoryDto;
import com.example.trabalhoDevWeb.models.*;
import com.example.trabalhoDevWeb.repositories.TaskRepository;
import com.example.trabalhoDevWeb.repositories.TypeTaskRepository;
import com.example.trabalhoDevWeb.repositories.TypeUserHistoryRepository;
import com.example.trabalhoDevWeb.repositories.UserHistoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TaskController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TypeTaskRepository typeTaskRepository;
    @Autowired
    UserHistoryRepository userHistoryRepository;
    @PostMapping("/tasks")
    public ResponseEntity<TaskModel> saveTask(@RequestBody @Valid TaskDto taskDto) {
        var taskModel = new TaskModel();
        taskModel.setId(taskDto.id());

        TypeTaskModel typeTaskModel = typeTaskRepository.findById(taskDto.typeTask_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + taskDto.typeTask_id()));
        taskModel.setTypeTask(typeTaskModel);

        UserHistoryModel userHistoryModel = userHistoryRepository.findById(taskDto.userHistory_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + taskDto.userHistory_id()));
        taskModel.setUserHistory(userHistoryModel);

        BeanUtils.copyProperties(taskDto, taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskModel>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.findAll());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id") String id) {
        Optional<TaskModel> taskSelected = taskRepository.findById(id);

        if(taskSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(taskSelected.get());
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") String id,
                                                    @RequestBody @Valid TaskDto taskDto) {
        Optional<TaskModel> taskSelected = taskRepository.findById(id);

        if(taskSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found");
        }

        var taskModel = taskSelected.get();
        BeanUtils.copyProperties(taskDto, taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }
}
