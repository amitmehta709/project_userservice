package com.scaler.amit.project_userservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseModel {
    private String username;
    private String hashedPassword;
    private String email;
}
