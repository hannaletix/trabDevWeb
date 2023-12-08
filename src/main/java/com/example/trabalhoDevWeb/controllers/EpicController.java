package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.EpicDto;
import com.example.trabalhoDevWeb.models.*;
import com.example.trabalhoDevWeb.services.EpicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/epic")
public class EpicController {
    private final EpicService epicService;

    @Autowired
    public EpicController(EpicService epicService) {
        this.epicService = epicService;
    }

    @PostMapping
    public ResponseEntity<Object> saveEpic(@RequestBody @Valid EpicDto epicDto) {
        Epic savedEpic = epicService.saveEpic(epicDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEpic);
    }

    @GetMapping
    public ResponseEntity<List<Epic>> getAllEpics() {
        List<Epic> allEpics = epicService.getAllEpics();
        return ResponseEntity.status(HttpStatus.OK).body(allEpics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEpic(@PathVariable(value = "id") long id) {
        return epicService.getOneEpic(id)
                .map(epic -> ResponseEntity.status(HttpStatus.OK).body((Object) epic))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Epic not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEpic(@PathVariable(value = "id") long id,
                                             @RequestBody @Valid EpicDto epicDto) {
        return epicService.updateEpic(id, epicDto)
                .map(updatedEpic -> ResponseEntity.status(HttpStatus.CREATED).body((Object) updatedEpic))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Epic not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEpic(@PathVariable(value = "id") long id) {
        boolean isDeleted = epicService.deleteEpic(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Epic deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Epic not found");
    }

    // Criada para auxiliar nos testes
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteAllEpics() {
        epicService.deleteAllEpics();
        return ResponseEntity.status(HttpStatus.OK).body("All Epics deleted successfully");
    }
}
