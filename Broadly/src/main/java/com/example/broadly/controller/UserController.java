package com.example.broadly.controller;

import com.example.broadly.dto.UserRequestDto;
import com.example.broadly.dto.UserResponseDto;
import com.example.broadly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin 
public class UserController {

    @Autowired
    private UserService userService;


    //Image upload
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponseDto uploadUser(
            @RequestPart String username,
            @RequestPart String email,
            @RequestPart String password,
            @RequestPart MultipartFile profileImage
    ) throws IOException {

        // create uploads folder if not exists
        Files.createDirectories(Paths.get("uploads"));

        // save file
        String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
        Files.copy(profileImage.getInputStream(), Paths.get("uploads", fileName));

        // prepare dto
        UserRequestDto dto = new UserRequestDto();
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setProfileImage("/uploads/" + fileName);

        return userService.createUser(dto);
    }


    @PostMapping
    public UserResponseDto create(@RequestBody UserRequestDto dto){
        return userService.createUser(dto);
    }
    // GET ALL USERS
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id,
                                      @RequestBody UserRequestDto dto) {
        return userService.updateUser(id, dto);
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

}
