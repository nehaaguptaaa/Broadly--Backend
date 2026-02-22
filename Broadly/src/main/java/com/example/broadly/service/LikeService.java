package com.example.broadly.service;

public interface LikeService {

    void likeShot(Long userId, Long shotId);

    void unlikeShot(Long userId, Long shotId);

    long getLikeCount(Long shotId);
}

