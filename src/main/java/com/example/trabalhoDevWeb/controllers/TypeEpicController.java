package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeEpicDto;
import com.example.trabalhoDevWeb.models.TypeEpic;
import com.example.trabalhoDevWeb.repositories.TypeEpicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/typeEpics")
public class TypeEpicController {
    @Autowired
    TypeEpicRepository typeEpicRepository;

    @PostMapping
    public ResponseEntity<TypeEpic> saveTypeEpic(@RequestBody @Valid TypeEpicDto typeEpicDto) {
        var typeEpicModel = new TypeEpic();
        typeEpicModel.setId(typeEpicDto.id());
        BeanUtils.copyProperties(typeEpicDto, typeEpicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeEpicRepository.save(typeEpicModel));
    }

    @GetMapping
    public ResponseEntity<List<TypeEpic>> getAllTypeEpics() {
        return ResponseEntity.status(HttpStatus.OK).body(typeEpicRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTypeEpic(@PathVariable(value = "id") String id) {
        Optional<TypeEpic> typeEpicSelected = typeEpicRepository.findById(id);

        if(typeEpicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(typeEpicSelected.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTypeEpic(@PathVariable(value = "id") String id,
                                                 @RequestBody @Valid TypeEpicDto typeEpicDto) {
        Optional<TypeEpic> typeEpicSelected = typeEpicRepository.findById(id);

        if(typeEpicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        var typeEpicModel = typeEpicSelected.get();
        BeanUtils.copyProperties(typeEpicDto, typeEpicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeEpicRepository.save(typeEpicModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTypeEpic(@PathVariable(value = "id") String id) {
        Optional<TypeEpic> typeEpicSelected = typeEpicRepository.findById(id);

        if(typeEpicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        typeEpicRepository.delete(typeEpicSelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("Type Epic deleted sucessfully");
    }
}
