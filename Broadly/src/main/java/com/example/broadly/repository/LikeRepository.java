package com.example.broadly.repository;

import com.example.broadly.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndShotId(Long userId, Long shotId);

    void deleteByUserIdAndShotId(Long userId, Long shotId);

    long countByShotId(Long shotId);
}

