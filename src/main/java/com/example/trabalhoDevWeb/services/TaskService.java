package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.TaskDto;
import com.example.trabalhoDevWeb.models.Task;
import com.example.trabalhoDevWeb.models.TypeTask;
import com.example.trabalhoDevWeb.models.UserHistory;
import com.example.trabalhoDevWeb.repositories.TaskRepository;
import com.example.trabalhoDevWeb.repositories.TypeTaskRepository;
import com.example.trabalhoDevWeb.repositories.UserHistoryRepository;
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

    @Autowired
    public TaskService(TaskRepository taskRepository, TypeTaskRepository typeTaskRepository, UserHistoryRepository userHistoryRepository) {
        this.taskRepository = taskRepository;
        this.typeTaskRepository = typeTaskRepository;
        this.userHistoryRepository = userHistoryRepository;
    }

    public Task saveTask(TaskDto taskDto) {
        Task taskModel = new Task();

        // Relação com typeTask
        TypeTask typeTaskModel = typeTaskRepository.findById(taskDto.typeTask_id())
                .orElseThrow(() -> new RuntimeException("Type Task not found with id: " + taskDto.typeTask_id()));
        taskModel.setTypeTask(typeTaskModel);

        // Relação com userHistory
        UserHistory userHistoryModel = userHistoryRepository.findById(taskDto.userHistory_id())
                .orElseThrow(() -> new RuntimeException("User History not found with id: " + taskDto.userHistory_id()));
        taskModel.setUserHistory(userHistoryModel);

        BeanUtils.copyProperties(taskDto, taskModel);

        return taskRepository.save(taskModel);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getOneTask(long id) {
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
            return true;
        }

        return false;
    }
}

