package com.example.broadly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShotResponseDto {

    private Long id;
    private String title;
    private String description;
    private String image;
    private String link;

    private Long boardId;
    private Long userId;

    private String username;
    private String userProfileImage;
    private LocalDateTime createdAt;
    private String boardName;

    // getters & setters
}

