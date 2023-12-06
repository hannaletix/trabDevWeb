package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.TypeUserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeUserHistoryRepository extends JpaRepository<TypeUserHistory, String> {
    List<TypeUserHistory> findByTypeEpic_Id(long typeEpicId);
}
