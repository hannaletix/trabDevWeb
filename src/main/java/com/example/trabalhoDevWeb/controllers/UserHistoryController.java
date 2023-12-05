package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.UserHistoryDto;
import com.example.trabalhoDevWeb.models.*;
import com.example.trabalhoDevWeb.repositories.EpicRepository;
import com.example.trabalhoDevWeb.repositories.TypeUserHistoryRepository;
import com.example.trabalhoDevWeb.repositories.UserHistoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userHistory")
public class UserHistoryController {

    @Autowired
    UserHistoryRepository userHistoryRepository;
    @Autowired
    TypeUserHistoryRepository typeUserHistoryRepository;
    @Autowired
    EpicRepository epicRepository;
    @PostMapping
    public ResponseEntity<UserHistory> saveUserHistory(@RequestBody @Valid UserHistoryDto userHistoryDto) {
        var userHistoryModel = new UserHistory();
        userHistoryModel.setId(userHistoryDto.id());

        TypeUserHistory typeUserHistoryModel = typeUserHistoryRepository.findById(userHistoryDto.typeUserHistory_id())
                .orElseThrow(() -> new RuntimeException("Type User History not found with id: " + userHistoryDto.typeUserHistory_id()));
        userHistoryModel.setTypeUserHistory(typeUserHistoryModel);

        Epic epicModel = epicRepository.findById(userHistoryDto.epic_id())
                .orElseThrow(() -> new RuntimeException("Epic not found with id: " + userHistoryDto.epic_id()));
        userHistoryModel.setEpic(epicModel);

        BeanUtils.copyProperties(userHistoryDto, userHistoryModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userHistoryRepository.save(userHistoryModel));
    }

    @GetMapping
    public ResponseEntity<List<UserHistory>> getAllUserHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(userHistoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUserHistory(@PathVariable(value = "id") String id) {
        Optional<UserHistory> userHistorySelected = userHistoryRepository.findById(id);

        if(userHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User History not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userHistorySelected.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserHistory(@PathVariable(value = "id") String id,
                                              @RequestBody @Valid UserHistoryDto userHistoryDto) {
        Optional<UserHistory> userHistorySelected = userHistoryRepository.findById(id);

        if(userHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User History not found");
        }

        var userHistoryModel = userHistorySelected.get();
        BeanUtils.copyProperties(userHistoryDto, userHistoryModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userHistoryRepository.save(userHistoryModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserHistory(@PathVariable(value = "id") String id) {
        Optional<UserHistory> userHistorySelected = userHistoryRepository.findById(id);

        if(userHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User History not found");
        }

        userHistoryRepository.delete(userHistorySelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("User History deleted sucessfully");
    }
}
