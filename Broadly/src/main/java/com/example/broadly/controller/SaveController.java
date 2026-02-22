package com.example.broadly.controller;

import com.example.broadly.dto.ShotResponseDto;
import com.example.broadly.service.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saves")
@CrossOrigin
public class SaveController {

    @Autowired
    private SaveService saveService;

    // SAVE
    @PostMapping
    public String saveShot(
            @RequestParam Long userId,
            @RequestParam Long shotId
    ) {
        saveService.saveShot(userId, shotId);
        return "Shot saved";
    }

    // UNSAVE
    @DeleteMapping
    public String unsaveShot(
            @RequestParam Long userId,
            @RequestParam Long shotId
    ) {
        saveService.unsaveShot(userId, shotId);
        return "Shot unsaved";
    }

    // GET SAVED SHOTS
    @GetMapping("/user/{userId}")
    public List<ShotResponseDto> getSavedShots(@PathVariable Long userId) {
        return saveService.getSavedShots(userId);
    }
}

