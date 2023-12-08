package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeUserHistoryDto;
import com.example.trabalhoDevWeb.models.TypeUserHistory;
import com.example.trabalhoDevWeb.services.TypeUserHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/typeUserHistory")
public class TypeUserHistoryController {
    private final TypeUserHistoryService typeUserHistoryService;

    @Autowired
    public TypeUserHistoryController(TypeUserHistoryService typeUserHistoryService) {
        this.typeUserHistoryService = typeUserHistoryService;
    }

    @PostMapping
    public ResponseEntity<Object> saveTypeUserHistory(@RequestBody @Valid TypeUserHistoryDto typeUserHistoryDto) {
        TypeUserHistory savedTypeUserHistory = typeUserHistoryService.saveTypeUserHistory(typeUserHistoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTypeUserHistory);
    }

    @GetMapping
    public ResponseEntity<List<TypeUserHistory>> getAllTypeUserHistory() {
        List<TypeUserHistory> allTypeUserHistory = typeUserHistoryService.getAllTypeUserHistory();
        return ResponseEntity.status(HttpStatus.OK).body(allTypeUserHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTypeUserHistory(@PathVariable(value = "id") long id) {
        return typeUserHistoryService.getOneTypeUserHistory(id)
                .map(typeUserHistory -> ResponseEntity.status(HttpStatus.OK).body((Object) typeUserHistory))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTypeUserHistory(@PathVariable(value = "id") long id,
                                                        @RequestBody @Valid TypeUserHistoryDto typeUserHistoryDto) {
        return typeUserHistoryService.updateTypeUserHistory(id, typeUserHistoryDto)
                .map(updatedTypeUserHistory -> ResponseEntity.status(HttpStatus.CREATED).body((Object) updatedTypeUserHistory))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTypeUserHistory(@PathVariable(value = "id") long id) {
        boolean isDeleted = typeUserHistoryService.deleteTypeUserHistory(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Type User History deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type User History not found");
    }
}

