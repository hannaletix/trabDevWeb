package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeTaskDto;
import com.example.trabalhoDevWeb.models.TypeTask;
import com.example.trabalhoDevWeb.repositories.TypeTaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/typeTask")
public class TypeTaskController {
    @Autowired
    TypeTaskRepository typeTaskRepository;

    @PostMapping
    public ResponseEntity<TypeTask> saveTypeTask(@RequestBody @Valid TypeTaskDto typeTaskDto) {
        var typeTaskModel = new TypeTask();
        BeanUtils.copyProperties(typeTaskDto, typeTaskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeTaskRepository.save(typeTaskModel));
    }

    @GetMapping
    public ResponseEntity<List<TypeTask>> getAllTypeTask() {
        return ResponseEntity.status(HttpStatus.OK).body(typeTaskRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTypeTask(@PathVariable(value = "id") long id) {
        Optional<TypeTask> typeTaskSelected = typeTaskRepository.findById(id);

        if(typeTaskSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(typeTaskSelected.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTypeTask(@PathVariable(value = "id") long id,
                                                 @RequestBody @Valid TypeTaskDto typeTaskDto) {
        Optional<TypeTask> typeTaskSelected = typeTaskRepository.findById(id);

        if(typeTaskSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found");
        }

        var typeTaskModel = typeTaskSelected.get();
        BeanUtils.copyProperties(typeTaskDto, typeTaskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeTaskRepository.save(typeTaskModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTypeTask(@PathVariable(value = "id") long id) {
        Optional<TypeTask> typeTaskSelected = typeTaskRepository.findById(id);

        if(typeTaskSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found");
        }

        typeTaskRepository.delete(typeTaskSelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("Type task deleted sucessfully");
    }
}
