package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.EpicDto;
import com.example.trabalhoDevWeb.models.*;
import com.example.trabalhoDevWeb.repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EpicService {
    private final EpicRepository epicRepository;
    private final TypeEpicRepository typeEpicRepository;
    private final TypeUserHistoryRepository typeUserHistoryRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final ProjectRepository projectRepository;
    private final TypeTaskRepository typeTaskRepository;
    private final TaskRepository taskRepository;

    // Constante usada para extrair as frases
    private static final String KEYWORD = "desejo";

    @Autowired
    public EpicService(
            EpicRepository epicRepository,
            TypeEpicRepository typeEpicRepository,
            TypeUserHistoryRepository typeUserHistoryRepository,
            UserHistoryRepository userHistoryRepository,
            ProjectRepository projectRepository,
            TypeTaskRepository typeTaskRepository,
            TaskRepository taskRepository) {
        this.epicRepository = epicRepository;
        this.typeEpicRepository = typeEpicRepository;
        this.typeUserHistoryRepository = typeUserHistoryRepository;
        this.userHistoryRepository = userHistoryRepository;
        this.projectRepository = projectRepository;
        this.typeTaskRepository = typeTaskRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Epic saveEpic(EpicDto epicDto) {
        try {
            Epic epic = createEpicFromDto(epicDto);

            // Relação com typeEpic
            TypeEpic typeEpic = typeEpicRepository.findById(epicDto.typeEpic_id())
                    .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + epicDto.typeEpic_id()));
            epic.setTypeEpic(typeEpic);

            // Relação com project
            Project project = projectRepository.findById(epicDto.project_id())
                    .orElseThrow(() -> new RuntimeException("Project not found with id: " + epicDto.project_id()));
            epic.setProject(project);
            epicRepository.save(epic);

            processUserHistoriesAsync(epic);

            return epic;
        } catch (Exception e) {
            System.out.println("Erro ao salvar o Epic: " + e.getMessage());
            throw e;
        }
    }
    @Async
    public void processUserHistoriesAsync(Epic epic) {
        // Lista de TypeUserHistory correspondentes ao TypeEpic do Epic
        List<TypeUserHistory> typeUserHistories = typeUserHistoryRepository.findByTypeEpic_Id(epic.getTypeEpic().getId());

        // Gerando o UserHistory
        for (TypeUserHistory typeUserHistory : typeUserHistories) {
            UserHistory userHistory = generateUserHistory(epic, typeUserHistory);
            userHistoryRepository.save(userHistory);

            // Lista de TypeTask correspondentes ao TypeUserHistory
            List<TypeTask> typeTasks = typeTaskRepository.findByTypeUserHistory(typeUserHistory);

            // Gerando as tasks
            for (TypeTask typeTask : typeTasks) {
                Task task = generateTask(userHistory, typeTask);
                taskRepository.save(task);
            }
        }
    }

    private UserHistory generateUserHistory(Epic epic, TypeUserHistory typeUserHistory) {
        UserHistory userHistory = new UserHistory();
        userHistory.setRelevancia(epic.getRelevancia());
        userHistory.setCategoria(epic.getCategoria());
        userHistory.setTypeUserHistory(typeUserHistory);
        userHistory.setEpic(epic);

        String descricaoEpic = epic.getDescricao();
        String descricaoTypeUserHistory = typeUserHistory.getDescricao();
        String titleUserHistory = generateTitleUserHistory(descricaoEpic, descricaoTypeUserHistory);
        userHistory.setTitulo(titleUserHistory);

        return userHistory;
    }

    private Epic createEpicFromDto(EpicDto epicDto) {
        Epic epic = new Epic();
        epic.setTitulo(epicDto.titulo());
        epic.setDescricao(epicDto.descricao());
        epic.setRelevancia(epicDto.relevancia());
        epic.setCategoria(epicDto.categoria());

        TypeEpic typeEpic = typeEpicRepository.findById(epicDto.typeEpic_id())
                .orElseThrow(() -> new RuntimeException("TypeEpic not found with id: " + epicDto.typeEpic_id()));
        epic.setTypeEpic(typeEpic);

        Project project = projectRepository.findById(epicDto.project_id())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + epicDto.project_id()));
        epic.setProject(project);

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

    private String generateTitleUserHistory(String descricaoEpic, String descricaoTypeUserHistory) {
        return descricaoEpic.replaceFirst(KEYWORD + "\\s+\\w+", KEYWORD + " " + descricaoTypeUserHistory);
    }

    private Task generateTask(UserHistory userHistory, TypeTask typeTask) {
        Task task = new Task();
        task.setTypeTask(typeTask);
        task.setUserHistory(userHistory);

        String titleTask = generateTaskTitle(userHistory, typeTask);
        task.setTitulo(titleTask);

        return task;
    }

    private String generateTaskTitle(UserHistory userHistory, TypeTask typeTask) {
        String typeUserHistoryDescription = userHistory.getTypeUserHistory().getDescricao();
        String typeTaskDescription = typeTask.getDescricao();

        String content = extractPhraseContent(userHistory.getTitulo());

        return typeUserHistoryDescription + " " + typeTaskDescription + " de " + content;
    }

    // Retorna o conteúdo após a palavra "desejo"
    private String extractPhraseContent(String title) {
        if (title.contains(KEYWORD)) {
            int index = title.indexOf(KEYWORD);

            return title.substring(index + KEYWORD.length()).trim();
        }

        return "";
    };

    // Criada para auxiliar nos testes
    @Transactional
    public void deleteAllEpics() {
        epicRepository.deleteAll();
    }
}

