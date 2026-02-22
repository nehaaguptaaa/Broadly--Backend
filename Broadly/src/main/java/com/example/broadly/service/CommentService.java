package com.example.broadly.service;

import com.example.broadly.dto.CommentRequestDto;
import com.example.broadly.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto addComment(CommentRequestDto dto);

    List<CommentResponseDto> getCommentsByShot(Long shotId);

    void deleteComment(Long commentId);
}

