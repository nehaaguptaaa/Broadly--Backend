package com.example.broadly.controller;

import com.example.broadly.dto.ShotRequestDto;
import com.example.broadly.dto.ShotResponseDto;
import com.example.broadly.service.ShotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/shots")
@CrossOrigin
public class ShotController {

    @Autowired
    private ShotService shotService;

    // CREATE
    @PostMapping
    public ShotResponseDto createShot(@RequestBody ShotRequestDto dto) {
        return shotService.createShot(dto);
    }

    // GET ALL
    @GetMapping
    public List<ShotResponseDto> getAllShots() {
        return shotService.getAllShots();
    }

    // GET BY BOARD
    @GetMapping("/board/{boardId}")
    public List<ShotResponseDto> getShotsByBoard(@PathVariable Long boardId) {
        return shotService.getShotsByBoard(boardId);
    }

    // GET BY USER
    @GetMapping("/user/{userId}")
    public List<ShotResponseDto> getShotsByUser(@PathVariable Long userId) {
        return shotService.getShotsByUser(userId);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ShotResponseDto getShotById(@PathVariable Long id) {
        return shotService.getShotById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ShotResponseDto updateShot(
            @PathVariable Long id,
            @RequestBody ShotRequestDto dto) {
        return shotService.updateShot(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteShot(@PathVariable Long id) {
        shotService.deleteShot(id);
        return "Shot deleted successfully";
    }
    //IMAGE UPLOAD
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ShotResponseDto createShotWithImage(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam("boardId") Long boardId,
            @RequestParam("userId") Long userId,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        // 1. Create uploads/shots folder
        Files.createDirectories(Paths.get("uploads/shots"));

        // 2. Save image
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Files.copy(
                image.getInputStream(),
                Paths.get("uploads/shots", fileName)
        );

        // 3. Prepare ShotRequestDto
        ShotRequestDto dto = new ShotRequestDto();
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setLink(link);
        dto.setBoardId(boardId);
        dto.setUserId(userId);
        dto.setImage("/uploads/shots/" + fileName); // ONLY URL stored in DB

        // 4. Save shot
        return shotService.createShot(dto);
    }

}

