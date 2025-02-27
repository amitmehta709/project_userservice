package com.scaler.amit.project_userservice.dtos;

import com.scaler.amit.project_userservice.models.Role;
import com.scaler.amit.project_userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private List<String> roles;

    public static UserDto fromUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        if(user.getAddress() != null){
            String addrs = user.getAddress().getStreet() +"," +
                    user.getAddress().getCity() + "," +
                    user.getAddress().getState() + "," +
                    user.getAddress().getCountry() + " - " +
                    user.getAddress().getZipcode();
            userDto.setAddress(addrs);
        }

        if(user.getRoles() != null){
            List<String> roles = new ArrayList<>();
            for(Role role : user.getRoles())
            {
                roles.add(role.getName());
            }
            userDto.setRoles(roles);
        }

        return userDto;
    }
}
