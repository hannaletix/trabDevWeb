package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.TypeTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTaskRepository extends JpaRepository<TypeTask, Long> {
}
