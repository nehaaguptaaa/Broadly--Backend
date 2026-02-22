package com.example.broadly.service;

import com.example.broadly.dto.ShotRequestDto;
import com.example.broadly.dto.ShotResponseDto;

import java.util.List;

public interface ShotService {

    ShotResponseDto createShot(ShotRequestDto dto);

    List<ShotResponseDto> getAllShots();

    List<ShotResponseDto> getShotsByBoard(Long boardId);

    List<ShotResponseDto> getShotsByUser(Long userId);

    ShotResponseDto getShotById(Long id);

    ShotResponseDto updateShot(Long id, ShotRequestDto dto);

    void deleteShot(Long id);
}

