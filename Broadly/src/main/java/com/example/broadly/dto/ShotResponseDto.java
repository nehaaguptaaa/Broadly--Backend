package com.example.broadly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // getters & setters
}

