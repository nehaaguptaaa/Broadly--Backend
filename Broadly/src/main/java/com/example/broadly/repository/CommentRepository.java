package com.example.broadly.repository;

import com.example.broadly.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByShotId(Long shotId);

    List<Comment> findByUserId(Long userId);
}

