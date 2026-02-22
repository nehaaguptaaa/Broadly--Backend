package com.example.broadly.security;

import com.example.broadly.dto.UserRequestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private UserRequestDto userDto;
}
