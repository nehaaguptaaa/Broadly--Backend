package com.example.broadly.service;

import com.example.broadly.dto.ProfileResponseDto;
import com.example.broadly.dto.UserRequestDto;
import com.example.broadly.dto.UserResponseDto;
import com.example.broadly.dto.UserSearchResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long id);
    ProfileResponseDto getProfile(String username);
    List<UserSearchResponseDto> searchUsers(String query);
}

