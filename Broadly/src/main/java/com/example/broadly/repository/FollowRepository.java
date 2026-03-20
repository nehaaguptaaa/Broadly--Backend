package com.example.broadly.repository;

import com.example.broadly.entity.Follow;
import com.example.broadly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    long countByFollowerId(Long userId);   // following count
    long countByFollowingId(Long userId);
    long countByFollowing(User user);
    long countByFollower(User user);// followers count
}
