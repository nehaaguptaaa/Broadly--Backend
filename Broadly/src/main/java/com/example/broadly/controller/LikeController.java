package com.example.broadly.controller;

import com.example.broadly.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin
public class LikeController {

    @Autowired
    private LikeService likeService;

    // LIKE
    @PostMapping
    public String likeShot(
            @RequestParam Long userId,
            @RequestParam Long shotId
    ) {
        likeService.likeShot(userId, shotId);
        return "Shot liked";
    }

    // UNLIKE
    @DeleteMapping
    public String unlikeShot(
            @RequestParam Long userId,
            @RequestParam Long shotId
    ) {
        likeService.unlikeShot(userId, shotId);
        return "Shot unliked";
    }

    // COUNT LIKES
    @GetMapping("/count/{shotId}")
    public long getLikeCount(@PathVariable Long shotId) {
        return likeService.getLikeCount(shotId);
    }
}

