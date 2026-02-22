package com.example.broadly.service;

import com.example.broadly.dto.ShotResponseDto;
import com.example.broadly.entity.Save;
import com.example.broadly.entity.Shot;
import com.example.broadly.entity.User;
import com.example.broadly.repository.SaveRepository;
import com.example.broadly.repository.ShotRepository;
import com.example.broadly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveServiceImpl implements SaveService {

    @Autowired
    private SaveRepository saveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShotRepository shotRepository;

    private ShotResponseDto mapToShotDto(Shot shot) {
        ShotResponseDto dto = new ShotResponseDto();
        dto.setId(shot.getId());
        dto.setTitle(shot.getTitle());
        dto.setDescription(shot.getDescription());
        dto.setImage(shot.getImage());
        dto.setLink(shot.getLink());
        dto.setBoardId(shot.getBoard().getId());
        dto.setUserId(shot.getUser().getId());
        return dto;
    }

    @Override
    public void saveShot(Long userId, Long shotId) {

        if (saveRepository.existsByUserIdAndShotId(userId, shotId)) {
            return; // already saved
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shot shot = shotRepository.findById(shotId)
                .orElseThrow(() -> new RuntimeException("Shot not found"));

        Save save = new Save();
        save.setUser(user);
        save.setShot(shot);

        saveRepository.save(save);
    }

    @Override
    public void unsaveShot(Long userId, Long shotId) {
        saveRepository.deleteByUserIdAndShotId(userId, shotId);
    }

    @Override
    public List<ShotResponseDto> getSavedShots(Long userId) {

        return saveRepository.findByUserId(userId)
                .stream()
                .map(save -> mapToShotDto(save.getShot()))
                .toList();
    }
}
