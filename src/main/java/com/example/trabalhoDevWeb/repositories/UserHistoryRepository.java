package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.UserHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistoryModel, String> {
}
