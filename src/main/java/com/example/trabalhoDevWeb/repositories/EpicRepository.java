package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.Epic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicRepository extends JpaRepository<Epic, Long> {
}
