package com.example.broadly.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private Long id;
    @NotBlank
    @Pattern(regexp = "^[a-z0-9._]+$",
            message = "Username can contain only lowercase letters, numbers, dot and underscore"
    )
    private String username;

    @NotBlank(message = "Name is required")
    @Size(max = 18, message = "Name cannot exceed 18 characters")
    private String name;

    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"
    )
    private String password;


    @Size(max = 200, message = "Bio cannot exceed 200 characters")
    private String bio;
    private String profileImage;
}

