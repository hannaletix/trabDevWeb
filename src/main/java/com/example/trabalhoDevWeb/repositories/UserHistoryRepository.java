package com.example.trabalhoDevWeb.repositories;

import com.example.trabalhoDevWeb.models.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
}
