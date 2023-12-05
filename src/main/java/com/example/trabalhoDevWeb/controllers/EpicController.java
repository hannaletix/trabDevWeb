package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.EpicDto;
import com.example.trabalhoDevWeb.models.Epic;
import com.example.trabalhoDevWeb.models.TypeEpic;
import com.example.trabalhoDevWeb.models.TypeUserHistory;
import com.example.trabalhoDevWeb.models.UserHistory;
import com.example.trabalhoDevWeb.repositories.EpicRepository;
import com.example.trabalhoDevWeb.repositories.TypeEpicRepository;
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
import java.util.UUID;

@RestController
@RequestMapping("/epics")
public class EpicController {
    @Autowired
    EpicRepository epicRepository;
    @Autowired
    TypeEpicRepository typeEpicRepository;
    @Autowired
    TypeUserHistoryRepository typeUserHistoryRepository;
    @Autowired
    UserHistoryRepository userHistoryRepository;
    @PostMapping
    public ResponseEntity<Epic> saveEpic(@RequestBody @Valid EpicDto epicDto) {
        var epic = new Epic();
        epic.setId(epicDto.id());
        epic.setTitulo(epicDto.titulo());
        epic.setDescricao(epicDto.descricao());
        epic.setRelevancia(epicDto.relevancia());
        epic.setCategoria(epicDto.categoria());

        TypeEpic typeEpic = typeEpicRepository.findById(epicDto.typeEpic_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + epicDto.typeEpic_id()));

        epic.setTypeEpic(typeEpic);
        epicRepository.save(epic);

        // Obtendo todos os TypeUserHistory correspondentes ao TypeEpic do Epic
        List<TypeUserHistory> typeUserHistories = typeUserHistoryRepository.findByTypeEpic_Id(typeEpic.getId());

        // Gerando o UserHistory
        for (TypeUserHistory typeUserHistory : typeUserHistories) {
            UserHistory userHistory = new UserHistory();

            String id = UUID.randomUUID().toString();
            userHistory.setId(id);

            userHistory.setTitulo(epic.getTitulo());
            userHistory.setRelevancia(epic.getRelevancia());
            userHistory.setCategoria(epic.getCategoria());
            userHistory.setTypeUserHistory(typeUserHistory);
            userHistory.setEpic(epic);

            String descricaoEpic = epic.getDescricao();
            String descricaoTypeUserHistory = typeUserHistory.getDescricao();
            String descricaoUserHistory = generateDescriptionUserHistory(descricaoEpic, descricaoTypeUserHistory);
            userHistory.setDescricao(descricaoUserHistory);

            userHistoryRepository.save(userHistory);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(epic);
    }

    private String generateDescriptionUserHistory(String descricaoEpic, String descricaoTypeUserHistory) {
        return descricaoEpic.replaceFirst("desejo\\s+\\w+", "desejo " + descricaoTypeUserHistory);
    }

    @GetMapping
    public ResponseEntity<List<Epic>> getAllEpics() {
        return ResponseEntity.status(HttpStatus.OK).body(epicRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEpic(@PathVariable(value = "id") String id) {
        Optional<Epic> epicSelected = epicRepository.findById(id);

        if(epicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Epic not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(epicSelected.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEpic(@PathVariable(value = "id") String id,
                                                        @RequestBody @Valid EpicDto epicDto) {
        Optional<Epic> epicSelected = epicRepository.findById(id);

        if(epicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Epic not found");
        }

        var epicModel = epicSelected.get();
        BeanUtils.copyProperties(epicDto, epicModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(epicRepository.save(epicModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEpic(@PathVariable(value = "id") String id) {
        Optional<Epic> epicSelected = epicRepository.findById(id);

        if(epicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Epic not found");
        }

        epicRepository.delete(epicSelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("Epic deleted sucessfully");
    }
}
