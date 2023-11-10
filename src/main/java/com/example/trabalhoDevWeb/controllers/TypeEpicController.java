package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeEpicDto;
import com.example.trabalhoDevWeb.models.TypeEpicModel;
import com.example.trabalhoDevWeb.repositories.TypeEpicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
}
