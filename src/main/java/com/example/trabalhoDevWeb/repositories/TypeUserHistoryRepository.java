package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.TypeUserHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeUserHistoryRepository extends JpaRepository<TypeUserHistoryModel, String> {
}
