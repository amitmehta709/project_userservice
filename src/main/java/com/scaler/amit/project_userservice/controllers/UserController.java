package com.scaler.amit.project_userservice.controllers;

import com.scaler.amit.project_userservice.dtos.SignUpRequestDto;
import com.scaler.amit.project_userservice.dtos.UserDto;
import com.scaler.amit.project_userservice.exceptions.DuplicateRecordsException;
import com.scaler.amit.project_userservice.exceptions.InvalidDataException;
import com.scaler.amit.project_userservice.exceptions.InvalidPasswordException;
import com.scaler.amit.project_userservice.models.User;
import com.scaler.amit.project_userservice.services.TokenService;
import com.scaler.amit.project_userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto requestDto) throws DuplicateRecordsException, InvalidPasswordException, InvalidDataException {
        User user = userService.createUser(requestDto.getEmail(),
                requestDto.getPassword(), requestDto.getName(), requestDto.getStreet(), requestDto.getCity(),
                requestDto.getState(), requestDto.getZipcode(), requestDto.getCountry(), requestDto.getRoles());

        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/getuser/all")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')") //This will enable role based access
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> userList = userService.getAllUser();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(UserDto.fromUser(user));
        }
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/getuser/{email}")
    public ResponseEntity<UserDto> getUsersByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

    @GetMapping("/getuser/{id}")
    public ResponseEntity<UserDto> getAllUsers(@PathVariable Long id) {
        User user = userService.getUserByEmail(id);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }
}
