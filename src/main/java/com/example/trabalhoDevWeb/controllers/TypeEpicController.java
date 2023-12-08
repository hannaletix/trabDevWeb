package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.TypeEpicDto;
import com.example.trabalhoDevWeb.models.TypeEpic;
import com.example.trabalhoDevWeb.services.TypeEpicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/typeEpic")
public class TypeEpicController {
    private final TypeEpicService typeEpicService;
    @Autowired
    public TypeEpicController(TypeEpicService typeEpicService) {
        this.typeEpicService = typeEpicService;
    }

    @PostMapping
    public ResponseEntity<TypeEpic> saveTypeEpic(@RequestBody @Valid TypeEpicDto typeEpicDto) {
        TypeEpic savedTypeEpic = typeEpicService.saveTypeEpic(typeEpicDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTypeEpic);
    }

    @GetMapping
    public ResponseEntity<List<TypeEpic>> getAllTypeEpics() {
        List<TypeEpic> allTypeEpics = typeEpicService.getAllTypeEpics();
        return ResponseEntity.status(HttpStatus.OK).body(allTypeEpics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTypeEpic(@PathVariable(value = "id") long id) {
        return typeEpicService.getOneTypeEpic(id)
                .map(typeEpic -> ResponseEntity.status(HttpStatus.OK).body((Object) typeEpic))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTypeEpic(@PathVariable(value = "id") long id,
                                                 @RequestBody @Valid TypeEpicDto typeEpicDto) {
        return typeEpicService.updateTypeEpic(id, typeEpicDto)
                .map(updatedTypeEpic -> ResponseEntity.status(HttpStatus.CREATED).body((Object) updatedTypeEpic))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTypeEpic(@PathVariable(value = "id") long id) {
        boolean isDeleted = typeEpicService.deleteTypeEpic(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Type Epic deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
    }
}
