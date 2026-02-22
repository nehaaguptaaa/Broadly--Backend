package com.example.broadly.service;

import com.example.broadly.entity.Like;
import com.example.broadly.entity.Shot;
import com.example.broadly.entity.User;
import com.example.broadly.repository.LikeRepository;
import com.example.broadly.repository.ShotRepository;
import com.example.broadly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShotRepository shotRepository;

    @Override
    public void likeShot(Long userId, Long shotId) {

        if (likeRepository.existsByUserIdAndShotId(userId, shotId)) {
            return; // already liked
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shot shot = shotRepository.findById(shotId)
                .orElseThrow(() -> new RuntimeException("Shot not found"));

        Like like = new Like();
        like.setUser(user);
        like.setShot(shot);

        likeRepository.save(like);
    }

    @Override
    public void unlikeShot(Long userId, Long shotId) {
        likeRepository.deleteByUserIdAndShotId(userId, shotId);
    }

    @Override
    public long getLikeCount(Long shotId) {
        return likeRepository.countByShotId(shotId);
    }
}

