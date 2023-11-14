package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.TypeTaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTaskRepository extends JpaRepository<TypeTaskModel, String> {
}
