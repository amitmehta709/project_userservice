package com.scaler.amit.project_userservice.services;

import com.scaler.amit.project_userservice.dtos.UserDto;
import com.scaler.amit.project_userservice.exceptions.DuplicateRecordsException;
import com.scaler.amit.project_userservice.models.Address;
import com.scaler.amit.project_userservice.models.Token;
import com.scaler.amit.project_userservice.models.User;
import com.scaler.amit.project_userservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(String email, String password, String name, String street,
                           String city, String state, String zip, String country) throws DuplicateRecordsException {

        //1. Verify if the user exists
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isEmpty()) {
            throw new DuplicateRecordsException("User already present: " + email);
        }

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZipcode(zip);
        address.setCountry(country);

        User newUser = new User();
        newUser.setUsername(name);
        newUser.setEmail(email);
        newUser.setUsername(name);
        newUser.setHashedPassword(bCryptPasswordEncoder.encode(password));
        newUser.setAddress(address);
        return userRepository.save(newUser);
    }

}
