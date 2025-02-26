package com.scaler.amit.project_userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String name;
    private String hashedPassword;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",  referencedColumnName = "id")
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private String resetPasswordQuestion;
    private String resetPasswordAnswer;
}
