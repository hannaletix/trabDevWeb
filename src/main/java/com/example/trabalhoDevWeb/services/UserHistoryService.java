package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.dtos.UserHistoryDto;
import com.example.trabalhoDevWeb.models.Epic;
import com.example.trabalhoDevWeb.models.TypeUserHistory;
import com.example.trabalhoDevWeb.models.UserHistory;
import com.example.trabalhoDevWeb.repositories.EpicRepository;
import com.example.trabalhoDevWeb.repositories.TypeUserHistoryRepository;
import com.example.trabalhoDevWeb.repositories.UserHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;
    private final TypeUserHistoryRepository typeUserHistoryRepository;
    private final EpicRepository epicRepository;
    private final DependencyService dependencyService;

    @Autowired
    public UserHistoryService(UserHistoryRepository userHistoryRepository, TypeUserHistoryRepository typeUserHistoryRepository, EpicRepository epicRepository, DependencyService dependencyService) {
        this.userHistoryRepository = userHistoryRepository;
        this.typeUserHistoryRepository = typeUserHistoryRepository;
        this.epicRepository = epicRepository;
        this.dependencyService = dependencyService;
    }

    public UserHistory saveUserHistory(UserHistoryDto userHistoryDto) {
        try {
            UserHistory userHistoryModel = new UserHistory();

            TypeUserHistory typeUserHistoryModel = typeUserHistoryRepository.findById(userHistoryDto.typeUserHistory_id())
                    .orElseThrow(() -> new RuntimeException("Type User History not found with id: " + userHistoryDto.typeUserHistory_id()));
            userHistoryModel.setTypeUserHistory(typeUserHistoryModel);

            Epic epicModel = epicRepository.findById(userHistoryDto.epic_id())
                    .orElseThrow(() -> new RuntimeException("Epic not found with id: " + userHistoryDto.epic_id()));
            userHistoryModel.setEpic(epicModel);

            BeanUtils.copyProperties(userHistoryDto, userHistoryModel);

            return userHistoryRepository.save(userHistoryModel);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o UserHistory: " + e.getMessage());
            throw e;
        }
    }

    public List<UserHistory> getAllUserHistory() {
        return userHistoryRepository.findAll();
    }

    public Optional<UserHistory> getOneUserHistory(long id) {
        return userHistoryRepository.findById(id);
    }

    public Optional<UserHistory> updateUserHistory(long id, UserHistoryDto userHistoryDto) {
        Optional<UserHistory> userHistorySelected = userHistoryRepository.findById(id);

        if (userHistorySelected.isPresent()) {
            UserHistory userHistoryModel = userHistorySelected.get();
            BeanUtils.copyProperties(userHistoryDto, userHistoryModel);
            return Optional.of(userHistoryRepository.save(userHistoryModel));
        }

        return Optional.empty();
    }

    public boolean deleteUserHistory(long id) {
        Optional<UserHistory> userHistorySelected = userHistoryRepository.findById(id);

        if (userHistorySelected.isPresent()) {
            userHistoryRepository.delete(userHistorySelected.get());
            return true;
        }

        return false;
    }

    //Criada para auxiliar nos testes
    @Transactional
    public void deleteAllUserHistories() {
        userHistoryRepository.deleteAll();
        dependencyService.deleteAllUserHistories();
    }
}

