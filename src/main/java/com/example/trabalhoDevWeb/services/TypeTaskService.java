package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.TypeTaskDto;
import com.example.trabalhoDevWeb.models.TypeTask;
import com.example.trabalhoDevWeb.repositories.TypeTaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TypeTaskService {
    private final TypeTaskRepository typeTaskRepository;

    @Autowired
    public TypeTaskService(TypeTaskRepository typeTaskRepository) {
        this.typeTaskRepository = typeTaskRepository;
    }

    public TypeTask saveTypeTask(TypeTaskDto typeTaskDto) {
        TypeTask typeTaskModel = new TypeTask();
        BeanUtils.copyProperties(typeTaskDto, typeTaskModel);

        return typeTaskRepository.save(typeTaskModel);
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
}

