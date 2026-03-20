package com.example.broadly.controller;

import com.example.broadly.dto.ProfileResponseDto;
import com.example.broadly.dto.UserRequestDto;
import com.example.broadly.dto.UserResponseDto;
import com.example.broadly.dto.UserSearchResponseDto;
import com.example.broadly.entity.User;
import com.example.broadly.repository.UserRepository;
import com.example.broadly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin 
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


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

    // PROFILE
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        return ResponseEntity.ok(userService.getProfile(username));
    }

    //SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResponseDto>> searchUsers(
            @RequestParam String query) {

        return ResponseEntity.ok(userService.searchUsers(query));
    }


    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponseDto uploadAvatar(
            @PathVariable Long id,
            @RequestParam("profileImage") MultipartFile file
    ) throws IOException {
        Files.createDirectories(Paths.get("uploads/avatars"));
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() + ext;
        Files.copy(file.getInputStream(), Paths.get("uploads/avatars", fileName));

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfileImage("/uploads/avatars/" + fileName);
        userRepository.save(user);

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getActualUsername());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProfileImage(user.getProfileImage());
        return dto;
    }

    @GetMapping("/avatar/{id}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        String path = user.getProfileImage(); // /uploads/avatars/xyz.jpg
        Path filePath = Paths.get("." + path);

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] image = Files.readAllBytes(filePath);

        String contentType = Files.probeContentType(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        contentType != null ? contentType : "image/jpeg"
                ))
                .body(image);
    }
}

