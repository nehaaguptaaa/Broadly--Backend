package com.example.broadly.controller;

import com.example.broadly.dto.ShotRequestDto;
import com.example.broadly.dto.ShotResponseDto;
import com.example.broadly.service.ShotService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
        System.out.println("DEBUG image path: " + dto.getImage());
        System.out.println("DEBUG title: " + dto.getTitle());
        System.out.println("DEBUG userId: " + dto.getUserId());
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

    //get image

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

    //get image by id
    @GetMapping(value = "/{id}/image")
    public void serveShotImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        ShotResponseDto shot = shotService.getShotById(id);
        String imagePath = shot.getImage(); // /uploads/shots/filename.jpg
        InputStream is = Files.newInputStream(Paths.get("." + imagePath));
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        org.springframework.util.StreamUtils.copy(is, response.getOutputStream());
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
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ShotResponseDto createShotWithImage(
//            @RequestParam("title") String title,
//            @RequestParam(value = "description", required = false) String description,
//            @RequestParam(value = "link", required = false) String link,
//            @RequestParam("boardId") Long boardId,
//            @RequestParam("image") MultipartFile image,
//            org.springframework.security.core.Authentication authentication
//    ) throws IOException {
//
//        // 1. Get logged-in user from JWT
//        com.example.broadly.entity.User user =
//                (com.example.broadly.entity.User) authentication.getPrincipal();
//
//        // 2. Create uploads/shots folder
//        Files.createDirectories(Paths.get("uploads/shots"));
//
//        // 3. Save image
//        //String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//        String fileName = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() +
//                image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
//        Files.copy(image.getInputStream(), Paths.get("uploads/shots", fileName));
//
//        // 4. Prepare DTO
//        ShotRequestDto dto = new ShotRequestDto();
//        dto.setTitle(title);
//        dto.setDescription(description);
//        dto.setLink(link);
//        dto.setBoardId(boardId);
//
//        // THIS LINE LINKS POST TO LOGGED-IN USER
//        dto.setUserId(user.getId());
//
//        dto.setImage("/uploads/shots/" + fileName);
//
//        return shotService.createShot(dto);
//    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ShotResponseDto createShotWithImage(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam("boardId") Long boardId,
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "userId", required = false) Long userId,
            org.springframework.security.core.Authentication authentication
    ) throws IOException {

        // Get userId from param first, fallback to JWT
        Long resolvedUserId = userId;
        if (resolvedUserId == null && authentication != null) {
            com.example.broadly.entity.User user =
                    (com.example.broadly.entity.User) authentication.getPrincipal();
            resolvedUserId = user.getId();
        }

        Files.createDirectories(Paths.get("uploads/shots"));
        String ext = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        String fileName = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() + ext;
        Files.copy(image.getInputStream(), Paths.get("uploads/shots", fileName));

        ShotRequestDto dto = new ShotRequestDto();
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setLink(link);
        dto.setBoardId(boardId);
        dto.setUserId(resolvedUserId);
        dto.setImage("/uploads/shots/" + fileName);

        return shotService.createShot(dto);
    }

}

