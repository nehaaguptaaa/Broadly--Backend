package com.example.broadly.service;

public interface FollowService {

    void followUser(Long followerId, Long followingId);

    void unfollowUser(Long followerId, Long followingId);

    long getFollowerCount(Long userId);

    long getFollowingCount(Long userId);
}

