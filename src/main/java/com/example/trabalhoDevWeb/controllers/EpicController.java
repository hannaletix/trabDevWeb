package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.EpicDto;
import com.example.trabalhoDevWeb.models.EpicModel;
import com.example.trabalhoDevWeb.repositories.EpicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EpicController {

    @Autowired
    EpicRepository epicRepository;

    @PostMapping("/epics")
    public ResponseEntity<EpicModel> saveTask(@RequestBody @Valid EpicDto epicDto) {
        var epicModel = new EpicModel();
        epicModel.setId(epicDto.id());
        BeanUtils.copyProperties(epicDto, epicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(epicRepository.save(epicModel));
    }
}
