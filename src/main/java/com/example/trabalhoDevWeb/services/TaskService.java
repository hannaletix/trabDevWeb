package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.TaskDto;
import com.example.trabalhoDevWeb.libArvore.ArvoreAVLExemplo;
import com.example.trabalhoDevWeb.libArvore.ArvoreBinariaExemplo;
import com.example.trabalhoDevWeb.models.Task;
import com.example.trabalhoDevWeb.models.TypeTask;
import com.example.trabalhoDevWeb.models.UserHistory;
import com.example.trabalhoDevWeb.repositories.TaskRepository;
import com.example.trabalhoDevWeb.repositories.TypeTaskRepository;
import com.example.trabalhoDevWeb.repositories.UserHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TypeTaskRepository typeTaskRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final ArvoreBinariaExemplo<Long> arvoreTasks;
    private final DependencyService dependencyService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TypeTaskRepository typeTaskRepository, UserHistoryRepository userHistoryRepository, ArvoreBinariaExemplo<Long> arvoreTasks, DependencyService dependencyService) {
        this.taskRepository = taskRepository;
        this.typeTaskRepository = typeTaskRepository;
        this.userHistoryRepository = userHistoryRepository;
        this.arvoreTasks = arvoreTasks;
        this.dependencyService = dependencyService;
    }

    public Task saveTask(TaskDto taskDto) {
        Task task = new Task();

        // Relação com typeTask
        TypeTask typeTaskModel = typeTaskRepository.findById(taskDto.typeTask_id())
                .orElseThrow(() -> new RuntimeException("Type Task not found with id: " + taskDto.typeTask_id()));
        task.setTypeTask(typeTaskModel);

        // Relação com userHistory
        UserHistory userHistoryModel = userHistoryRepository.findById(taskDto.userHistory_id())
                .orElseThrow(() -> new RuntimeException("User History not found with id: " + taskDto.userHistory_id()));
        task.setUserHistory(userHistoryModel);

        BeanUtils.copyProperties(taskDto, task);

        arvoreTasks.adicionar(task.getId()); // Adiciona a task na árvore

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getOneTask(long id) {
        // Pesquisa na árvore
        arvoreTasks.pesquisar(id);

        return taskRepository.findById(id);
    }

    public Optional<Task> updateTask(long id, TaskDto taskDto) {
        Optional<Task> taskSelected = taskRepository.findById(id);

        if (taskSelected.isPresent()) {
            Task taskModel = taskSelected.get();
            BeanUtils.copyProperties(taskDto, taskModel);

            return Optional.of(taskRepository.save(taskModel));
        }

        return Optional.empty();
    }

    public boolean deleteTask(long id) {
        Optional<Task> taskSelected = taskRepository.findById(id);

        if (taskSelected.isPresent()) {
            taskRepository.delete(taskSelected.get());

            // Remove a tarefa da árvore, se ela existir
            arvoreTasks.remover(id);

            // Imprime a árvore de tasks para verificar se a exclusão funcionou
            System.out.println("Árvore de tasks: " + arvoreTasks.caminharEmOrdem());

            return true;
        }

        return false;
    }

    // Criada para auxiliar nos testes
    @Transactional
    public void deleteAllTasks() {
        taskRepository.deleteAll();
        arvoreTasks.limparArvore();

        // Imprime a árvore de tasks para verificar se a exclusão funcionou
        System.out.println("Árvore de tasks: " + arvoreTasks.caminharEmOrdem());

        dependencyService.deleteAllTasks();
    }
}

