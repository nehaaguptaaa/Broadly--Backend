package com.example.broadly.service;

import com.example.broadly.dto.ShotRequestDto;
import com.example.broadly.dto.ShotResponseDto;
import com.example.broadly.entity.Board;
import com.example.broadly.entity.Shot;
import com.example.broadly.entity.User;
import com.example.broadly.repository.BoardRepository;
import com.example.broadly.repository.ShotRepository;
import com.example.broadly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShotServiceImpl implements ShotService {

    @Autowired
    private ShotRepository shotRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    // ---------- Mapper ----------
    private ShotResponseDto mapToDto(Shot shot) {
        ShotResponseDto dto = new ShotResponseDto();
        dto.setId(shot.getId());
        dto.setTitle(shot.getTitle());
        dto.setDescription(shot.getDescription());
        dto.setImage(shot.getImage());
        dto.setLink(shot.getLink());
        dto.setBoardId(shot.getBoard().getId());
        dto.setUserId(shot.getUser().getId());
        dto.setUsername(shot.getUser().getActualUsername());
        dto.setUserProfileImage(shot.getUser().getProfileImage());
        return dto;
    }

    // ---------- CREATE ----------
    @Override
    public ShotResponseDto createShot(ShotRequestDto dto) {

        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shot shot = new Shot();
        shot.setTitle(dto.getTitle());
        shot.setDescription(dto.getDescription());
        shot.setImage(dto.getImage());
        shot.setLink(dto.getLink());
        shot.setBoard(board);
        shot.setUser(user);

        return mapToDto(shotRepository.save(shot));
    }

    // ---------- READ ALL ----------
    @Override
    public List<ShotResponseDto> getAllShots() {
        return shotRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // ---------- READ BY BOARD ----------
    @Override
    public List<ShotResponseDto> getShotsByBoard(Long boardId) {
        return shotRepository.findByBoardId(boardId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // ---------- READ BY USER ----------
    @Override
    public List<ShotResponseDto> getShotsByUser(Long userId) {
        return shotRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // ---------- READ BY ID ----------
    @Override
    public ShotResponseDto getShotById(Long id) {
        Shot shot = shotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shot not found"));
        return mapToDto(shot);
    }

    // ---------- UPDATE ----------
    @Override
    public ShotResponseDto updateShot(Long id, ShotRequestDto dto) {

        Shot shot = shotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shot not found"));

        shot.setTitle(dto.getTitle());
        shot.setDescription(dto.getDescription());
        shot.setImage(dto.getImage());
        shot.setLink(dto.getLink());

        return mapToDto(shotRepository.save(shot));
    }

    // ---------- DELETE ----------
    @Override
    public void deleteShot(Long id) {
        shotRepository.deleteById(id);
    }


}

