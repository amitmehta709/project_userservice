package com.scaler.amit.project_userservice.services;

import com.scaler.amit.project_userservice.dtos.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDto createUser(String email, String password, String name)
    {
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setName(name);
        return userDto;
    }
}
