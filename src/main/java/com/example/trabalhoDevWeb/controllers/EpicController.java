package com.example.trabalhoDevWeb.controllers;

import com.example.trabalhoDevWeb.dtos.EpicDto;
import com.example.trabalhoDevWeb.models.*;
import com.example.trabalhoDevWeb.repositories.*;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/epic")
public class EpicController {
    @Autowired
    EpicRepository epicRepository;
    @Autowired
    TypeEpicRepository typeEpicRepository;
    @Autowired
    TypeUserHistoryRepository typeUserHistoryRepository;
    @Autowired
    UserHistoryRepository userHistoryRepository;
    @Autowired
    ProjectRepository projectRepository;

    @PostMapping
    public ResponseEntity<Epic> saveEpic(@RequestBody @Valid EpicDto epicDto) {
        var epic = new Epic();
        epic.setTitulo(epicDto.titulo());
        epic.setDescricao(epicDto.descricao());
        epic.setRelevancia(epicDto.relevancia());
        epic.setCategoria(epicDto.categoria());

        // Relação com typeEpic
        TypeEpic typeEpic = typeEpicRepository.findById(epicDto.typeEpic_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + epicDto.typeEpic_id()));
        epic.setTypeEpic(typeEpic);

        // Relação com project
        Project project = projectRepository.findById(epicDto.project_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + epicDto.project_id()));
        epic.setProject(project);
        epicRepository.save(epic);

        // Obtendo todos os TypeUserHistory correspondentes ao TypeEpic do Epic
        List<TypeUserHistory> typeUserHistories = typeUserHistoryRepository.findByTypeEpic_Id(typeEpic.getId());

        // Iterando sobre a lista e imprimindo informações
        for (TypeUserHistory typeUserHistory : typeUserHistories) {
            System.out.println("TypeUserHistory ID: " + typeUserHistory.getId());
            System.out.println("TypeUserHistory Nome: " + typeUserHistory.getDescricao());
            // Adicione mais campos conforme necessário
            System.out.println("-------------------------");
        }

        // Gerando o UserHistory
        for (TypeUserHistory typeUserHistory : typeUserHistories) {
            UserHistory userHistory = new UserHistory();
            userHistory.setRelevancia(epic.getRelevancia());
            userHistory.setCategoria(epic.getCategoria());
            userHistory.setTypeUserHistory(typeUserHistory);
            userHistory.setEpic(epic);

            String descricaoEpic = epic.getDescricao();
            String descricaoTypeUserHistory = typeUserHistory.getDescricao();
            String titleUserHistory = generateDescriptionUserHistory(descricaoEpic, descricaoTypeUserHistory);
            userHistory.setTitulo(titleUserHistory);

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
    public ResponseEntity<Object> getOneEpic(@PathVariable(value = "id") long id) {
        Optional<Epic> epicSelected = epicRepository.findById(id);

        if(epicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Epic not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(epicSelected.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEpic(@PathVariable(value = "id") long id,
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
    public ResponseEntity<Object> deleteEpic(@PathVariable(value = "id") long id) {
        Optional<Epic> epicSelected = epicRepository.findById(id);

        if(epicSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Epic not found");
        }

        epicRepository.delete(epicSelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("Epic deleted sucessfully");
    }
}
