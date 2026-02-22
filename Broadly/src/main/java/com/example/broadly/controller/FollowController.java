package com.example.broadly.controller;

import com.example.broadly.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
@CrossOrigin
public class FollowController {

    @Autowired
    private FollowService followService;

    // FOLLOW
    @PostMapping
    public String follow(
            @RequestParam Long followerId,
            @RequestParam Long followingId
    ) {
        followService.followUser(followerId, followingId);
        return "User followed";
    }

    // UNFOLLOW
    @DeleteMapping
    public String unfollow(
            @RequestParam Long followerId,
            @RequestParam Long followingId
    ) {
        followService.unfollowUser(followerId, followingId);
        return "User unfollowed";
    }

    // FOLLOWERS COUNT
    @GetMapping("/followers/{userId}")
    public long followers(@PathVariable Long userId) {
        return followService.getFollowerCount(userId);
    }

    // FOLLOWING COUNT
    @GetMapping("/following/{userId}")
    public long following(@PathVariable Long userId) {
        return followService.getFollowingCount(userId);
    }
}
