package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeEpicDto;
import com.example.trabalhoDevWeb.models.TypeEpicModel;
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
public class TypeEpicController {
    @Autowired
    TypeEpicRepository typeEpicRepository;

    @PostMapping("/typeEpics")
    public ResponseEntity<TypeEpicModel> saveTypeEpic(@RequestBody @Valid TypeEpicDto typeEpicDto) {
        var typeEpicModel = new TypeEpicModel();
        typeEpicModel.setId(typeEpicDto.id());
        BeanUtils.copyProperties(typeEpicDto, typeEpicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeEpicRepository.save(typeEpicModel));
    }

    @GetMapping("/typeEpics")
    public ResponseEntity<List<TypeEpicModel>> getAllTypeEpics() {
        return ResponseEntity.status(HttpStatus.OK).body(typeEpicRepository.findAll());
    }

    @GetMapping("/typeEpics/{id}")
    public ResponseEntity<Object> getOneTypeEpic(@PathVariable(value = "id") String id) {
        Optional<TypeEpicModel> typeEpicSelected = typeEpicRepository.findById(id);

        if(typeEpicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(typeEpicSelected.get());
    }

    @PutMapping("/typeEpics/{id}")
    public ResponseEntity<Object> updateTypeEpic(@PathVariable(value = "id") String id,
                                                 @RequestBody @Valid TypeEpicDto typeEpicDto) {
        Optional<TypeEpicModel> typeEpicSelected = typeEpicRepository.findById(id);

        if(typeEpicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        var typeEpicModel = typeEpicSelected.get();
        BeanUtils.copyProperties(typeEpicDto, typeEpicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeEpicRepository.save(typeEpicModel));
    }
}
