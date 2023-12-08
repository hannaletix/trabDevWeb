package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.TypeTask;
import com.example.trabalhoDevWeb.models.TypeUserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TypeTaskRepository extends JpaRepository<TypeTask, Long> {
    List<TypeTask> findByTypeUserHistory(TypeUserHistory typeUserHistory);
}
