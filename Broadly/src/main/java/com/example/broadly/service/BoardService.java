package com.example.broadly.service;

import com.example.broadly.dto.BoardRequestDto;
import com.example.broadly.dto.BoardResponseDto;

import java.util.List;

public interface BoardService {

    BoardResponseDto createBoard(BoardRequestDto dto);

    List<BoardResponseDto> getAllBoards();

    List<BoardResponseDto> getBoardsByUser(Long userId);

    BoardResponseDto getBoardById(Long id);

    BoardResponseDto updateBoard(Long id, BoardRequestDto dto);

    void deleteBoard(Long id);
}

