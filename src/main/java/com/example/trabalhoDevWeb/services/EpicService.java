package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.EpicDto;
import com.example.trabalhoDevWeb.models.Epic;
import com.example.trabalhoDevWeb.models.Project;
import com.example.trabalhoDevWeb.models.TypeEpic;
import com.example.trabalhoDevWeb.models.TypeUserHistory;
import com.example.trabalhoDevWeb.models.UserHistory;
import com.example.trabalhoDevWeb.repositories.EpicRepository;
import com.example.trabalhoDevWeb.repositories.ProjectRepository;
import com.example.trabalhoDevWeb.repositories.TypeEpicRepository;
import com.example.trabalhoDevWeb.repositories.TypeUserHistoryRepository;
import com.example.trabalhoDevWeb.repositories.UserHistoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EpicService {
    private final EpicRepository epicRepository;
    private final TypeEpicRepository typeEpicRepository;
    private final TypeUserHistoryRepository typeUserHistoryRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public EpicService(
            EpicRepository epicRepository,
            TypeEpicRepository typeEpicRepository,
            TypeUserHistoryRepository typeUserHistoryRepository,
            UserHistoryRepository userHistoryRepository,
            ProjectRepository projectRepository) {
        this.epicRepository = epicRepository;
        this.typeEpicRepository = typeEpicRepository;
        this.typeUserHistoryRepository = typeUserHistoryRepository;
        this.userHistoryRepository = userHistoryRepository;
        this.projectRepository = projectRepository;
    }

    public Epic saveEpic(EpicDto epicDto) {
        Epic epic = new Epic();
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
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + epicDto.project_id()));
        epic.setProject(project);
        epicRepository.save(epic);

        // Obtendo todos os TypeUserHistory correspondentes ao TypeEpic do Epic
        List<TypeUserHistory> typeUserHistories = typeUserHistoryRepository.findByTypeEpic_Id(typeEpic.getId());

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

        return epic;
    }

    public List<Epic> getAllEpics() {
        return epicRepository.findAll();
    }

    public Optional<Epic> getOneEpic(long id) {
        return epicRepository.findById(id);
    }

    public Optional<Epic> updateEpic(long id, EpicDto epicDto) {
        Optional<Epic> epicSelected = epicRepository.findById(id);

        if (epicSelected.isPresent()) {
            Epic epicModel = epicSelected.get();
            BeanUtils.copyProperties(epicDto, epicModel);
            return Optional.of(epicRepository.save(epicModel));
        }

        return Optional.empty();
    }

    public boolean deleteEpic(long id) {
        Optional<Epic> epicSelected = epicRepository.findById(id);

        if (epicSelected.isPresent()) {
            epicRepository.delete(epicSelected.get());
            return true;
        }

        return false;
    }

    private String generateDescriptionUserHistory(String descricaoEpic, String descricaoTypeUserHistory) {
        return descricaoEpic.replaceFirst("desejo\\s+\\w+", "desejo " + descricaoTypeUserHistory);
    }
}

