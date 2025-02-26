package com.scaler.amit.project_userservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private List<String> roles;
    private String resetPasswordQuestion;
    private String resetPasswordAnswer;
}
