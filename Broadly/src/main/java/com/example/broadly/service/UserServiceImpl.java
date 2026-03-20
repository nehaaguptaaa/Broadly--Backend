package com.example.broadly.service;

import com.example.broadly.dto.*;
import com.example.broadly.entity.AppRole;
import com.example.broadly.entity.Role;
import com.example.broadly.entity.Shot;
import com.example.broadly.entity.User;
import com.example.broadly.repository.FollowRepository;
import com.example.broadly.repository.RoleRepositary;
import com.example.broadly.repository.ShotRepository;
import com.example.broadly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private ShotRepository shotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepositary roleRepositary;

    // ---------- MAPPER METHODS ----------
    //user created from the request will be sent as response to user
    private UserResponseDto mapToResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getActualUsername());
        dto.setEmail(user.getEmail());
        dto.setProfileImage(user.getProfileImage());
        return dto;
    }

    //whatever the dto is coming from request is mapped as user object
    private User mapToEntity(UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setProfileImage(dto.getProfileImage());
        return user;
    }

    //CREATE
    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = mapToEntity(dto);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = roleRepositary.findByRoleName(AppRole.ROLE_USER).orElseThrow(()->new RuntimeException("Role Not Found"));
        user.setRole(role);
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    //READ
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //READ BY ID
    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    //UPDATE
    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setProfileImage(dto.getProfileImage());

        // update password only if sent
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }

        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    //DELETE
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    // PROFILE
    @Override
    public ProfileResponseDto getProfile(String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponseDto dto = new ProfileResponseDto();

        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setBio(user.getBio());
        dto.setProfileImage(user.getProfileImage());

        dto.setFollowersCount(followRepository.countByFollowing(user));
        dto.setFollowingCount(followRepository.countByFollower(user));
        dto.setPostsCount(shotRepository.countByUser(user));

        dto.setPosts(
                shotRepository.findByUser(user)
                        .stream()
                        .map(this::convertToShotResponseDto)
                        .toList()
        );

        return dto;
    }


    private ShotResponseDto convertToShotResponseDto(Shot shot) {

        ShotResponseDto dto = new ShotResponseDto();

        dto.setId(shot.getId());
        dto.setTitle(shot.getTitle());
        dto.setDescription(shot.getDescription());
        dto.setImage(shot.getImage());
        dto.setLink(shot.getLink());
        dto.setCreatedAt(shot.getCreatedAt());

        // Board info ( needed in frontend)
        if (shot.getBoard() != null) {
            dto.setBoardId(shot.getBoard().getId());
            dto.setBoardName(shot.getBoard().getName());
        }

        return dto;
    }

    //SEARCH
    @Override
    public List<UserSearchResponseDto> searchUsers(String query) {

        List<User> users = userRepository
                .findByUsernameContainingIgnoreCase(query);

        return users.stream().map(user -> {

            UserSearchResponseDto dto = new UserSearchResponseDto();
            dto.setId(user.getId());
            dto.setUsername(user.getActualUsername());
            dto.setName(user.getName());
            dto.setProfileImage(user.getProfileImage());

            return dto;

        }).toList();
    }
}
