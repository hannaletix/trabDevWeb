package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TaskDto;
import com.example.trabalhoDevWeb.models.TaskModel;
import com.example.trabalhoDevWeb.repositories.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @PostMapping("/tasks")
    public ResponseEntity<TaskModel> saveTask(@RequestBody @Valid TaskDto taskDto) {
        var taskModel = new TaskModel();
        taskModel.setId(taskDto.id());
        BeanUtils.copyProperties(taskDto, taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }
}
