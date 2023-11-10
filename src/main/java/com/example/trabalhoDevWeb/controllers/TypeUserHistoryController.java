package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeUserHistoryDto;
import com.example.trabalhoDevWeb.models.TypeTaskModel;
import com.example.trabalhoDevWeb.models.TypeUserHistoryModel;
import com.example.trabalhoDevWeb.repositories.TypeUserHistoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TypeUserHistoryController {
    @Autowired
    TypeUserHistoryRepository typeUserHistoryRepository;
    @PostMapping("/typeUserHistory")
    public ResponseEntity<TypeUserHistoryModel> saveTypeUserHistory(@RequestBody @Valid TypeUserHistoryDto typeUserHistoryDto) {
        var typeUserHistoryModel = new TypeUserHistoryModel();
        typeUserHistoryModel.setId(typeUserHistoryDto.id());
        BeanUtils.copyProperties(typeUserHistoryDto, typeUserHistoryModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeUserHistoryRepository.save(typeUserHistoryModel));
    }

    @GetMapping("/typeUserHistory")
    public ResponseEntity<List<TypeUserHistoryModel>> getAllTypeUserHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(typeUserHistoryRepository.findAll());
    }

    @GetMapping("/typeUserHistory/{id}")
    public ResponseEntity<Object> getOneTypeUserHistory(@PathVariable(value = "id") String id) {
        Optional<TypeUserHistoryModel> typeUserHistorySelected = typeUserHistoryRepository.findById(id);

        if(typeUserHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(typeUserHistorySelected.get());
    }
}
