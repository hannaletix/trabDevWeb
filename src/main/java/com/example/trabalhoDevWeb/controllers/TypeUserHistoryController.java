package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeUserHistoryDto;
import com.example.trabalhoDevWeb.models.TypeUserHistoryModel;
import com.example.trabalhoDevWeb.repositories.TypeUserHistoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
