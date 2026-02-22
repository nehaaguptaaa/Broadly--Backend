package com.example.broadly.service;

import com.example.broadly.dto.CommentRequestDto;
import com.example.broadly.dto.CommentResponseDto;
import com.example.broadly.entity.Comment;
import com.example.broadly.entity.Shot;
import com.example.broadly.entity.User;
import com.example.broadly.repository.CommentRepository;
import com.example.broadly.repository.ShotRepository;
import com.example.broadly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShotRepository shotRepository;

    private CommentResponseDto mapToDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setUserId(comment.getUser().getId());
        dto.setShotId(comment.getShot().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    @Override
    public CommentResponseDto addComment(CommentRequestDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shot shot = shotRepository.findById(dto.getShotId())
                .orElseThrow(() -> new RuntimeException("Shot not found"));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setUser(user);
        comment.setShot(shot);

        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getCommentsByShot(Long shotId) {
        return commentRepository.findByShotId(shotId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

