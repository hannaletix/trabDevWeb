package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TaskDto;
import com.example.trabalhoDevWeb.models.*;
import com.example.trabalhoDevWeb.repositories.TaskRepository;
import com.example.trabalhoDevWeb.repositories.TypeTaskRepository;
import com.example.trabalhoDevWeb.repositories.UserHistoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TypeTaskRepository typeTaskRepository;
    @Autowired
    UserHistoryRepository userHistoryRepository;
    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody @Valid TaskDto taskDto) {
        var taskModel = new Task();

        TypeTask typeTaskModel = typeTaskRepository.findById(taskDto.typeTask_id())
                .orElseThrow(() -> new RuntimeException("Type Task not found with id: " + taskDto.typeTask_id()));
        taskModel.setTypeTask(typeTaskModel);

        UserHistory userHistoryModel = userHistoryRepository.findById(taskDto.userHistory_id())
                .orElseThrow(() -> new RuntimeException("User History not found with id: " + taskDto.userHistory_id()));
        taskModel.setUserHistory(userHistoryModel);

        BeanUtils.copyProperties(taskDto, taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id") long id) {
        Optional<Task> taskSelected = taskRepository.findById(id);

        if(taskSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(taskSelected.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") long id,
                                                    @RequestBody @Valid TaskDto taskDto) {
        Optional<Task> taskSelected = taskRepository.findById(id);

        if(taskSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        var taskModel = taskSelected.get();
        BeanUtils.copyProperties(taskDto, taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") long id) {
        Optional<Task> taskSelected = taskRepository.findById(id);

        if(taskSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        taskRepository.delete(taskSelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted sucessfully");
    }
}
