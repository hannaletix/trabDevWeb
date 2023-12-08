package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.TypeEpicDto;
import com.example.trabalhoDevWeb.models.TypeEpic;
import com.example.trabalhoDevWeb.repositories.TypeEpicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TypeEpicService {
    private final TypeEpicRepository typeEpicRepository;

    @Autowired
    public TypeEpicService(TypeEpicRepository typeEpicRepository) {
        this.typeEpicRepository = typeEpicRepository;
    }

    public TypeEpic saveTypeEpic(TypeEpicDto typeEpicDto) {
        var typeEpicModel = new TypeEpic();
        BeanUtils.copyProperties(typeEpicDto, typeEpicModel);
        return typeEpicRepository.save(typeEpicModel);
    }

    public List<TypeEpic> getAllTypeEpics() {
        return typeEpicRepository.findAll();
    }

    public Optional<TypeEpic> getOneTypeEpic(long id) {
        return typeEpicRepository.findById(id);
    }

    public Optional<TypeEpic> updateTypeEpic(long id, TypeEpicDto typeEpicDto) {
        Optional<TypeEpic> typeEpicSelected = typeEpicRepository.findById(id);

        if (typeEpicSelected.isPresent()) {
            var typeEpicModel = typeEpicSelected.get();
            BeanUtils.copyProperties(typeEpicDto, typeEpicModel);
            return Optional.of(typeEpicRepository.save(typeEpicModel));
        }

        return Optional.empty();
    }

    public boolean deleteTypeEpic(long id) {
        Optional<TypeEpic> typeEpicSelected = typeEpicRepository.findById(id);

        if (typeEpicSelected.isPresent()) {
            typeEpicRepository.delete(typeEpicSelected.get());
            return true;
        }

        return false;
    }
}
