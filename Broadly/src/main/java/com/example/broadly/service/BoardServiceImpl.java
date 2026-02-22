package com.example.broadly.service;

import com.example.broadly.dto.BoardRequestDto;
import com.example.broadly.dto.BoardResponseDto;
import com.example.broadly.entity.Board;
import com.example.broadly.entity.User;
import com.example.broadly.repository.BoardRepository;
import com.example.broadly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    // ---------- MAPPER ----------
    private BoardResponseDto mapToDto(Board board) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.setId(board.getId());
        dto.setName(board.getName());
        dto.setDescription(board.getDescription());
        dto.setCoverImage(board.getCoverImage());
        dto.setUserId(board.getUser().getId());
        return dto;
    }

    // ---------- CREATE ----------
    @Override
    public BoardResponseDto createBoard(BoardRequestDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Board board = new Board();
        board.setName(dto.getName());
        board.setDescription(dto.getDescription());
        board.setCoverImage(dto.getCoverImage());
        board.setUser(user);

        return mapToDto(boardRepository.save(board));
    }

    // ---------- READ ALL ----------
    @Override
    public List<BoardResponseDto> getAllBoards() {
        return boardRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // ---------- READ BY USER ----------
    @Override
    public List<BoardResponseDto> getBoardsByUser(Long userId) {
        return boardRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // ---------- READ BY ID ----------
    @Override
    public BoardResponseDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        return mapToDto(board);
    }

    // ---------- UPDATE ----------
    @Override
    public BoardResponseDto updateBoard(Long id, BoardRequestDto dto) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        board.setName(dto.getName());
        board.setDescription(dto.getDescription());
        board.setCoverImage(dto.getCoverImage());

        return mapToDto(boardRepository.save(board));
    }

    // ---------- DELETE ----------
    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}

