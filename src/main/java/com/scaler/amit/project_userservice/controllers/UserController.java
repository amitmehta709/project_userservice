package com.scaler.amit.project_userservice.controllers;

import com.scaler.amit.project_userservice.dtos.LoginRequestDto;
import com.scaler.amit.project_userservice.dtos.LoginResponseDto;
import com.scaler.amit.project_userservice.dtos.SignUpRequestDto;
import com.scaler.amit.project_userservice.dtos.UserDto;
import com.scaler.amit.project_userservice.exceptions.DuplicateRecordsException;
import com.scaler.amit.project_userservice.exceptions.InvalidPasswordException;
import com.scaler.amit.project_userservice.models.Token;
import com.scaler.amit.project_userservice.models.User;
import com.scaler.amit.project_userservice.services.TokenService;
import com.scaler.amit.project_userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto requestDto) throws DuplicateRecordsException, InvalidPasswordException {
        User user = userService.createUser(requestDto.getEmail(),
                requestDto.getPassword(), requestDto.getName(), requestDto.getStreet(), requestDto.getCity(),
                requestDto.getState(), requestDto.getZipcode(), requestDto.getCountry());

        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        Token token = tokenService.login(requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>(LoginResponseDto.fromToken(token), HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public ResponseEntity<UserDto> validateUser(@PathVariable String token) {
        User user = tokenService.validateToken(token);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }
}
