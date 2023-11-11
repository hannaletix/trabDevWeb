package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.EpicDto;
import com.example.trabalhoDevWeb.dtos.UserHistoryDto;
import com.example.trabalhoDevWeb.models.EpicModel;
import com.example.trabalhoDevWeb.models.TaskModel;
import com.example.trabalhoDevWeb.models.TypeUserHistoryModel;
import com.example.trabalhoDevWeb.models.UserHistoryModel;
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
public class UserHistoryController {

    @Autowired
    UserHistoryRepository userHistoryRepository;
    @Autowired
    TypeUserHistoryRepository typeUserHistoryRepository;
    @Autowired
    EpicRepository epicRepository;
    @PostMapping("/userHistory")
    public ResponseEntity<UserHistoryModel> saveUserHistory(@RequestBody @Valid UserHistoryDto userHistoryDto) {
        var userHistoryModel = new UserHistoryModel();
        userHistoryModel.setId(userHistoryDto.id());

        TypeUserHistoryModel typeUserHistoryModel = typeUserHistoryRepository.findById(userHistoryDto.typeUserHistory_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + userHistoryDto.typeUserHistory_id()));
        userHistoryModel.setTypeUserHistory(typeUserHistoryModel);

        EpicModel epicModel = epicRepository.findById(userHistoryDto.epic_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + userHistoryDto.epic_id()));
        userHistoryModel.setEpic(epicModel);

        BeanUtils.copyProperties(userHistoryDto, userHistoryModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userHistoryRepository.save(userHistoryModel));
    }

    @GetMapping("/userHistory")
    public ResponseEntity<List<UserHistoryModel>> getAllUserHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(userHistoryRepository.findAll());
    }

    @GetMapping("/userHistory/{id}")
    public ResponseEntity<Object> getOneUserHistory(@PathVariable(value = "id") String id) {
        Optional<UserHistoryModel> userHistorySelected = userHistoryRepository.findById(id);

        if(userHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userHistorySelected.get());
    }

    @PutMapping("/userHistory/{id}")
    public ResponseEntity<Object> updateUserHistory(@PathVariable(value = "id") String id,
                                              @RequestBody @Valid UserHistoryDto userHistoryDto) {
        Optional<UserHistoryModel> userHistorySelected = userHistoryRepository.findById(id);

        if(userHistorySelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found");
        }

        var userHistoryModel = userHistorySelected.get();
        BeanUtils.copyProperties(userHistoryDto, userHistoryModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userHistoryRepository.save(userHistoryModel));
    }
}
