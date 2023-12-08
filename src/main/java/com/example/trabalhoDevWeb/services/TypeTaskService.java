package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.TypeTaskDto;
import com.example.trabalhoDevWeb.models.TypeTask;
import com.example.trabalhoDevWeb.models.TypeUserHistory;
import com.example.trabalhoDevWeb.repositories.TypeTaskRepository;
import com.example.trabalhoDevWeb.repositories.TypeUserHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TypeTaskService {
    private final TypeTaskRepository typeTaskRepository;
    private final TypeUserHistoryRepository typeUserHistoryRepository;

    @Autowired
    public TypeTaskService(TypeTaskRepository typeTaskRepository, TypeUserHistoryRepository typeUserHistoryRepository) {
        this.typeTaskRepository = typeTaskRepository;
        this.typeUserHistoryRepository = typeUserHistoryRepository;
    }

    public TypeTask saveTypeTask(TypeTaskDto typeTaskDto) {
        try {
            TypeTask typeTask = new TypeTask();
            typeTask.setDescricao(typeTaskDto.descricao());

            // Relação com typeUserHistory
            TypeUserHistory typeUserHistory = typeUserHistoryRepository.findById(typeTaskDto.typeUserHistory_id())
                    .orElse(null);
            typeTask.setTypeUserHistory(typeUserHistory);

            return typeTaskRepository.save(typeTask);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o TypeTask: " + e.getMessage());
            throw e;
        }
    }

    public List<TypeTask> getAllTypeTasks() {
        return typeTaskRepository.findAll();
    }

    public Optional<TypeTask> getOneTypeTask(long id) {
        return typeTaskRepository.findById(id);
    }

    public Optional<TypeTask> updateTypeTask(long id, TypeTaskDto typeTaskDto) {
        Optional<TypeTask> typeTaskSelected = typeTaskRepository.findById(id);

        if (typeTaskSelected.isPresent()) {
            TypeTask typeTaskModel = typeTaskSelected.get();
            BeanUtils.copyProperties(typeTaskDto, typeTaskModel);
            return Optional.of(typeTaskRepository.save(typeTaskModel));
        }

        return Optional.empty();
    }

    public boolean deleteTypeTask(long id) {
        Optional<TypeTask> typeTaskSelected = typeTaskRepository.findById(id);

        if (typeTaskSelected.isPresent()) {
            typeTaskRepository.delete(typeTaskSelected.get());
            return true;
        }

        return false;
    }

    // Criada para auxiliar nos testes
    @Transactional
    public void deleteAllTypeTasks() {
        typeTaskRepository.deleteAll();
    }
}

