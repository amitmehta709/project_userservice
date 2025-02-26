package com.scaler.amit.project_userservice.services;

import com.scaler.amit.project_userservice.dtos.ResetPasswordDto;
import com.scaler.amit.project_userservice.exceptions.DuplicateRecordsException;
import com.scaler.amit.project_userservice.exceptions.InvalidDataException;
import com.scaler.amit.project_userservice.exceptions.InvalidPasswordException;
import com.scaler.amit.project_userservice.models.Address;
import com.scaler.amit.project_userservice.models.Role;
import com.scaler.amit.project_userservice.models.User;
import com.scaler.amit.project_userservice.repositories.RoleRepository;
import com.scaler.amit.project_userservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(String email, String password, String name, String street,
                           String city, String state, String zip, String country, List<String> roles,
                           String resetPasswordQuestion, String resetPasswordAnswer) throws DuplicateRecordsException, InvalidPasswordException, InvalidDataException {

        if(email == null || password == null || name == null || street == null || city == null || state == null
                || zip == null || country == null || roles == null || resetPasswordQuestion == null
                || resetPasswordAnswer == null)
        {
            throw new InvalidDataException("Invalid data");
        }

        //1. Verify if the user exists
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isEmpty()) {
            throw new DuplicateRecordsException("User already present: " + email);
        }

        if(!isValidPassword(password))
        {
            throw new InvalidPasswordException("Invalid Password. Password should be at least 8 characters long " +
                    "and should have at least one digit, one uppercase letter, " +
                    "one lowercase letter and one special character");
        }

        //Create Roles if not present else use existing role
        List<Role> roleList = new ArrayList<>();
        if(!roles.isEmpty())
        {
            for(String role : roles)
            {
                Optional<Role> roleOptional = roleRepository.findByName(role);
                if(roleOptional.isPresent())
                {
                    roleList.add(roleOptional.get());
                }
                else
                {
                    Role newRole = new Role();
                    newRole.setName(role);
                    roleList.add(roleRepository.save(newRole));
                }
            }
        }
        else
        {
            throw new InvalidDataException("Roles is mandatory while creating user");
        }

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZipcode(zip);
        address.setCountry(country);

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setHashedPassword(bCryptPasswordEncoder.encode(password));
        newUser.setAddress(address);
        newUser.setRoles(roleList);
        newUser.setResetPasswordQuestion(resetPasswordQuestion);
        newUser.setResetPasswordAnswer(resetPasswordAnswer);
        return userRepository.save(newUser);
    }

    public User getUserByEmail(String email)  {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty())
        {
            throw new UsernameNotFoundException("User by email: " + email + " doesn't exist.");
        }
        return user.get();
    }

    public List<User> getAllUser()  {
        return userRepository.findAll();
    }

    public User resetPassword(ResetPasswordDto resetPasswordDto) throws InvalidDataException {
        Optional<User> optionlUser = userRepository.findByEmail(resetPasswordDto.getEmail());
        if(optionlUser.isEmpty())
        {
            throw new UsernameNotFoundException("User by Email: " + resetPasswordDto.getEmail()
                    + " doesn't exist.");
        }
        User user = optionlUser.get();
        String actualResetPasswordQuestion = user.getResetPasswordQuestion();
        String actualResetPasswordAnswer = user.getResetPasswordAnswer();

        if(!resetPasswordDto.getResetPasswordQuestion().equalsIgnoreCase(actualResetPasswordQuestion))
        {
            throw new InvalidDataException("Reset Password Question does not match.");
        }

        if(!resetPasswordDto.getResetPasswordAnswer().equalsIgnoreCase(actualResetPasswordAnswer))
        {
            throw new InvalidDataException("Reset Password Answer does not match.");
        }

        String newEncodedPassword = bCryptPasswordEncoder.encode(resetPasswordDto.getNewPassword());
        user.setHashedPassword(newEncodedPassword);
        return userRepository.save(user);
    }

    public String getResetPasswordQuestion(String email) throws InvalidDataException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User by Email: " + email + " doesn't exist.");
        }
        return user.get().getResetPasswordQuestion();
    }

    //Password Validation logic
    // Regex pattern explanation:
    // ^                 - Start of string
    // (?=.*[A-Z])       - At least one uppercase letter
    // (?=.*[a-z])       - At least one lowercase letter
    // (?=.*\\d)         - At least one digit
    // (?=.*[@$!%*?&])   - At least one special character
    // [A-Za-z\\d@$!%*?&]{8,} - Minimum 8 characters from allowed set
    // $                 - End of string
    private final String PASSWORD_REGEX =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    private boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
