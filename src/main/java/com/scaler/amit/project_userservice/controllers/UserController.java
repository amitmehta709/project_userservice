package com.scaler.amit.project_userservice.controllers;

import com.scaler.amit.project_userservice.dtos.ResetPasswordDto;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
                requestDto.getState(), requestDto.getZipcode(), requestDto.getCountry(), requestDto.getRoles(),
                requestDto.getResetPasswordQuestion(), requestDto.getResetPasswordAnswer());

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            // Extract the JWT token
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            String username = jwt.getClaim("sub");  // username is email
           if (!email.equalsIgnoreCase(username)) { // Case-insensitive check
               throw new AccessDeniedException("You cannot access another user's data.");
            }
        }
        else {
            throw new BadCredentialsException("Authentication is not valid.");
        }

        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

    @GetMapping("/getresetpasswordquestion/{email}")
    public ResponseEntity<String> getResetPasswordQuestion(@PathVariable String email) throws InvalidDataException {
        String question = userService.getResetPasswordQuestion(email);
        String jsonResponse = "{\"resetPasswordQuestion\":\""+question+"\"}";
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<UserDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws InvalidDataException {
        if(resetPasswordDto.getEmail()== null || resetPasswordDto.getResetPasswordQuestion() == null
        || resetPasswordDto.getResetPasswordAnswer() == null || resetPasswordDto.getNewPassword() == null){
            throw new InvalidDataException("Invalid Request Body.");
        }
        User user = userService.resetPassword(resetPasswordDto);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

    @PatchMapping("/updateuser/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            // Extract the JWT token
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            String userId = jwt.getClaim("userId");  // username is email
            if (!userId.equalsIgnoreCase(String.valueOf(id))) { // Case-insensitive check
                throw new AccessDeniedException("You cannot update another user's data.");
            }
        }
        else {
            throw new BadCredentialsException("Authentication is not valid.");
        }

        User updatedUser = userService.updateUser(id,updates);
        return new ResponseEntity<>(UserDto.fromUser(updatedUser), HttpStatus.OK);
    }

    @PatchMapping("/addrole/{id}")
    public ResponseEntity<UserDto> addRole(@PathVariable Long id, @RequestParam String roleName)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            // Extract the JWT token
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            String userId = jwt.getClaim("userId");  // username is email
            if (!userId.equalsIgnoreCase(String.valueOf(id))) { // Case-insensitive check
                throw new AccessDeniedException("You cannot update another user's data.");
            }
        }
        else {
            throw new BadCredentialsException("Authentication is not valid.");
        }

        User updatedUser = userService.addRole(id,roleName);
        return new ResponseEntity<>(UserDto.fromUser(updatedUser), HttpStatus.OK);
    }

    @PatchMapping("/removerole/{id}")
    public ResponseEntity<UserDto> removeRole(@PathVariable Long id, @RequestParam String roleName) throws InvalidDataException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            // Extract the JWT token
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            String userId = jwt.getClaim("userId");  // username is email
            if (!userId.equalsIgnoreCase(String.valueOf(id))) { // Case-insensitive check
                throw new AccessDeniedException("You cannot update another user's data.");
            }
        }
        else {
            throw new BadCredentialsException("Authentication is not valid.");
        }

        User updatedUser = userService.removeRole(id,roleName);
        return new ResponseEntity<>(UserDto.fromUser(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/deleteuser/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            // Extract the JWT token
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            String username = jwt.getClaim("sub");  // username is email
            if (!email.equalsIgnoreCase(username)) { // Case-insensitive check
                throw new AccessDeniedException("You cannot delete another user.");
            }
        }
        else {
            throw new BadCredentialsException("Authentication is not valid.");
        }

        userService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
