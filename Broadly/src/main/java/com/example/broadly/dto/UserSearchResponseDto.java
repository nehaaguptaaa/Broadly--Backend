package com.example.broadly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponseDto {

    private Long id;
    private String username;
    private String name;
    private String profileImage;


}
