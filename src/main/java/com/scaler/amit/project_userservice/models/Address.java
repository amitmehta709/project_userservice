package com.scaler.amit.project_userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address extends BaseModel {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
