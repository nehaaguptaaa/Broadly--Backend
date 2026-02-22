package com.example.broadly.service;

import com.example.broadly.dto.ShotResponseDto;

import java.util.List;

public interface SaveService {

    void saveShot(Long userId, Long shotId);

    void unsaveShot(Long userId, Long shotId);

    List<ShotResponseDto> getSavedShots(Long userId);
}

