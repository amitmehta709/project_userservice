package com.scaler.amit.project_userservice.controllers;

import com.scaler.amit.project_userservice.dtos.SignUpRequestDto;
import com.scaler.amit.project_userservice.dtos.UserDto;
import com.scaler.amit.project_userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto requestDto){
        UserDto user = userService.createUser(requestDto.getEmail(),
                requestDto.getPassword(), requestDto.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
