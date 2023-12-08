package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.UserHistoryDto;
import com.example.trabalhoDevWeb.models.UserHistory;
import com.example.trabalhoDevWeb.services.UserHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/userHistory")
public class UserHistoryController {
    private final UserHistoryService userHistoryService;

    @Autowired
    public UserHistoryController(UserHistoryService userHistoryService) {
        this.userHistoryService = userHistoryService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUserHistory(@RequestBody @Valid UserHistoryDto userHistoryDto) {
        UserHistory savedUserHistory = userHistoryService.saveUserHistory(userHistoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserHistory);
    }

    @GetMapping
    public ResponseEntity<List<UserHistory>> getAllUserHistory() {
        List<UserHistory> allUserHistory = userHistoryService.getAllUserHistory();
        return ResponseEntity.status(HttpStatus.OK).body(allUserHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUserHistory(@PathVariable(value = "id") long id) {
        return userHistoryService.getOneUserHistory(id)
                .map(userHistory -> ResponseEntity.status(HttpStatus.OK).body((Object) userHistory))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User History not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserHistory(@PathVariable(value = "id") long id,
                                                    @RequestBody @Valid UserHistoryDto userHistoryDto) {
        return userHistoryService.updateUserHistory(id, userHistoryDto)
                .map(updatedUserHistory -> ResponseEntity.status(HttpStatus.CREATED).body((Object) updatedUserHistory))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User History not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserHistory(@PathVariable(value = "id") long id) {
        boolean isDeleted = userHistoryService.deleteUserHistory(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("User History deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User History not found");
    }

    // Criada para auxiliar nos testes
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteAllUserHistories() {
        userHistoryService.deleteAllUserHistories();
        return ResponseEntity.status(HttpStatus.OK).body("All UserHistories deleted successfully");
    }
}

