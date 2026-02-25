package com.example.broadly.controller;

import com.example.broadly.dto.UserRequestDto;
import com.example.broadly.entity.User;
import com.example.broadly.repository.UserRepository;
import com.example.broadly.security.LoginRequest;
import com.example.broadly.security.LoginResponse;
import com.example.broadly.security.jwt.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private UserRepository userRepositary;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }
        catch (BadCredentialsException e) {
            System.out.println("bad crenentials");
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        User user = (User)authenticate.getPrincipal();
        String token = jwtUtils.generateTokenFromUsername(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        UserRequestDto userDto = modelMapper.map(user, UserRequestDto.class);
        loginResponse.setUserDto(userDto);
        return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
    }
}
