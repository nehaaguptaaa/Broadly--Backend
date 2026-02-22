package com.example.broadly.controller;

import com.example.broadly.dto.BoardRequestDto;
import com.example.broadly.dto.BoardResponseDto;
import com.example.broadly.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin
public class BoardController {

    @Autowired
    private BoardService boardService;

    // CREATE
    @PostMapping
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto dto) {
        return boardService.createBoard(dto);
    }

    // GET ALL BOARDS
    @GetMapping
    public List<BoardResponseDto> getAllBoards() {
        return boardService.getAllBoards();
    }

    // GET BOARDS BY USER
    @GetMapping("/user/{userId}")
    public List<BoardResponseDto> getBoardsByUser(@PathVariable Long userId) {
        return boardService.getBoardsByUser(userId);
    }

    // GET BOARD BY ID
    @GetMapping("/{id}")
    public BoardResponseDto getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    // UPDATE BOARD
    @PutMapping("/{id}")
    public BoardResponseDto updateBoard(
            @PathVariable Long id,
            @RequestBody BoardRequestDto dto) {
        return boardService.updateBoard(id, dto);
    }

    // DELETE BOARD
    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "Board deleted successfully";
    }

    //IMAGE UPLOAD ENDPOINT
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BoardResponseDto createBoardWithImage(
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("userId") Long userId,
            @RequestParam("coverImage") MultipartFile coverImage
    ) throws IOException {

        // 1. Create uploads folder
        Files.createDirectories(Paths.get("uploads"));

        // 2. Save image
        String fileName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();
        Files.copy(coverImage.getInputStream(), Paths.get("uploads", fileName));

        // 3. Prepare dto
        BoardRequestDto dto = new BoardRequestDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setCoverImage("/uploads/" + fileName);
        dto.setUserId(userId);

        return boardService.createBoard(dto);
    }

}

