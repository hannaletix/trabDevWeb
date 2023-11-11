package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.EpicDto;
import com.example.trabalhoDevWeb.dtos.TypeUserHistoryDto;
import com.example.trabalhoDevWeb.models.EpicModel;
import com.example.trabalhoDevWeb.models.TypeEpicModel;
import com.example.trabalhoDevWeb.models.TypeUserHistoryModel;
import com.example.trabalhoDevWeb.repositories.EpicRepository;
import com.example.trabalhoDevWeb.repositories.TypeEpicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EpicController {

    @Autowired
    EpicRepository epicRepository;
    @Autowired
    TypeEpicRepository typeEpicRepository;
    @PostMapping("/epics")
    public ResponseEntity<EpicModel> saveEpic(@RequestBody @Valid EpicDto epicDto) {
        var epicModel = new EpicModel();
        epicModel.setId(epicDto.id());

        TypeEpicModel typeEpicModel = typeEpicRepository.findById(epicDto.typeEpic_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + epicDto.typeEpic_id()));

        epicModel.setTypeEpic(typeEpicModel);

        BeanUtils.copyProperties(epicDto, epicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(epicRepository.save(epicModel));
    }

    @GetMapping("/epics")
    public ResponseEntity<List<EpicModel>> getAllEpics() {
        return ResponseEntity.status(HttpStatus.OK).body(epicRepository.findAll());
    }

    @GetMapping("/epics/{id}")
    public ResponseEntity<Object> getOneEpic(@PathVariable(value = "id") String id) {
        Optional<EpicModel> epicSelected = epicRepository.findById(id);

        if(epicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Epic not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(epicSelected.get());
    }

    @PutMapping("/epics/{id}")
    public ResponseEntity<Object> updateEpics(@PathVariable(value = "id") String id,
                                                        @RequestBody @Valid EpicDto epicDto) {
        Optional<EpicModel> epicSelected = epicRepository.findById(id);

        if(epicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Task not found");
        }

        var epicModel = epicSelected.get();
        BeanUtils.copyProperties(epicDto, epicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(epicRepository.save(epicModel));
    }
}
