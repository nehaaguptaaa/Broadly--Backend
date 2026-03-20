package com.example.broadly.repository;

import com.example.broadly.entity.Shot;
import com.example.broadly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShotRepository extends JpaRepository<Shot, Long> {

    List<Shot> findByBoardId(Long boardId);

    List<Shot> findByUserId(Long userId);
    long countByUser(User user);
    List<Shot> findByUser(User user);
}

