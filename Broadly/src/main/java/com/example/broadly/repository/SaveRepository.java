package com.example.broadly.repository;

import com.example.broadly.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveRepository extends JpaRepository<Save, Long> {

    boolean existsByUserIdAndShotId(Long userId, Long shotId);

    void deleteByUserIdAndShotId(Long userId, Long shotId);

    List<Save> findByUserId(Long userId);
}

