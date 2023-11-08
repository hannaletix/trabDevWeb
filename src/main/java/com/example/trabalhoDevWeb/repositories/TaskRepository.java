package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, String> {
}
