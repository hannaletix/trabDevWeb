package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.TypeEpic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeEpicRepository extends JpaRepository<TypeEpic, Long> {
}
