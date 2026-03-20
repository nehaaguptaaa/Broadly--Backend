package com.example.broadly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

    private String username;
    private String name;
    private String bio;
    private String profileImage;

    private long followersCount;
    private long followingCount;
    private long postsCount;

    private List<ShotResponseDto> posts;
    private List<BoardResponseDto> boards;
    private List<ShotResponseDto> savedPins;
}
