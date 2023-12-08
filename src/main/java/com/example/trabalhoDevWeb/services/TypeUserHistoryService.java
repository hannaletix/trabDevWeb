package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.TypeUserHistoryDto;
import com.example.trabalhoDevWeb.models.TypeEpic;
import com.example.trabalhoDevWeb.models.TypeUserHistory;
import com.example.trabalhoDevWeb.repositories.TypeEpicRepository;
import com.example.trabalhoDevWeb.repositories.TypeUserHistoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TypeUserHistoryService {
    private final TypeUserHistoryRepository typeUserHistoryRepository;
    private final TypeEpicRepository typeEpicRepository;

    @Autowired
    public TypeUserHistoryService(TypeUserHistoryRepository typeUserHistoryRepository, TypeEpicRepository typeEpicRepository) {
        this.typeUserHistoryRepository = typeUserHistoryRepository;
        this.typeEpicRepository = typeEpicRepository;
    }

    public TypeUserHistory saveTypeUserHistory(TypeUserHistoryDto typeUserHistoryDto) {
        try {
            TypeUserHistory typeUserHistory = new TypeUserHistory();
            typeUserHistory.setDescricao(typeUserHistoryDto.descricao());

            // Relação com TypeEpic
            TypeEpic typeEpic = typeEpicRepository.findById(typeUserHistoryDto.typeEpic_id())
                    .orElse(null);
            typeUserHistory.setTypeEpic(typeEpic);

            return typeUserHistoryRepository.save(typeUserHistory);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o TypeUserHistory: " + e.getMessage());
            throw e;
        }
    }

    public List<TypeUserHistory> getAllTypeUserHistory() {
        return typeUserHistoryRepository.findAll();
    }

    public Optional<TypeUserHistory> getOneTypeUserHistory(long id) {
        return typeUserHistoryRepository.findById(id);
    }

    public Optional<TypeUserHistory> updateTypeUserHistory(long id, TypeUserHistoryDto typeUserHistoryDto) {
        Optional<TypeUserHistory> typeUserHistorySelected = typeUserHistoryRepository.findById(id);

        if (typeUserHistorySelected.isPresent()) {
            TypeUserHistory typeUserHistory = typeUserHistorySelected.get();
            BeanUtils.copyProperties(typeUserHistoryDto, typeUserHistory);
            return Optional.of(typeUserHistoryRepository.save(typeUserHistory));
        }

        return Optional.empty();
    }

    public boolean deleteTypeUserHistory(long id) {
        Optional<TypeUserHistory> typeUserHistorySelected = typeUserHistoryRepository.findById(id);

        if (typeUserHistorySelected.isPresent()) {
            typeUserHistoryRepository.delete(typeUserHistorySelected.get());
            return true;
        }

        return false;
    }
}


