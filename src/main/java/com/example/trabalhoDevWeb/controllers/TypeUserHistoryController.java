package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeUserHistoryDto;
import com.example.trabalhoDevWeb.models.TypeEpic;
import com.example.trabalhoDevWeb.models.TypeUserHistory;
import com.example.trabalhoDevWeb.repositories.TypeEpicRepository;
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
@RequestMapping("/typeUserHistory")
public class TypeUserHistoryController {
    @Autowired
    TypeUserHistoryRepository typeUserHistoryRepository;
    @Autowired
    TypeEpicRepository typeEpicRepository;

    @PostMapping
    public ResponseEntity<TypeUserHistory> saveTypeUserHistory(@RequestBody @Valid TypeUserHistoryDto typeUserHistoryDto) {
        var typeUserHistory = new TypeUserHistory();
        typeUserHistory.setDescricao(typeUserHistoryDto.descricao());

        // Relação com TypeEpic
        TypeEpic typeEpic = typeEpicRepository.findById(typeUserHistoryDto.typeEpic_id()).orElse(null);
        typeUserHistory.setTypeEpic(typeEpic);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeUserHistoryRepository.save(typeUserHistory));
    }

    @GetMapping
    public ResponseEntity<List<TypeUserHistory>> getAllTypeUserHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(typeUserHistoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTypeUserHistory(@PathVariable(value = "id") long id) {
        Optional<TypeUserHistory> typeUserHistorySelected = typeUserHistoryRepository.findById(id);

        if(typeUserHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(typeUserHistorySelected.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTypeUserHistory(@PathVariable(value = "id") long id,
                                                 @RequestBody @Valid TypeUserHistoryDto typeUserHistoryDto) {
        Optional<TypeUserHistory> typeUserHistorySelected = typeUserHistoryRepository.findById(id);

        if(typeUserHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found");
        }

        var typeUserHistory = typeUserHistorySelected.get();
        BeanUtils.copyProperties(typeUserHistoryDto, typeUserHistory);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeUserHistoryRepository.save(typeUserHistory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserHistory(@PathVariable(value = "id") long id) {
        Optional<TypeUserHistory> typeUserHistorySelected = typeUserHistoryRepository.findById(id);

        if(typeUserHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found");
        }

        typeUserHistoryRepository.delete(typeUserHistorySelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("Type User History deleted sucessfully");
    }
}
