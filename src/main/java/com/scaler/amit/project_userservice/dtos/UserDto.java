package com.scaler.amit.project_userservice.dtos;

import com.scaler.amit.project_userservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private String address;

    public static UserDto fromUser(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getUsername());
        userDto.setEmail(user.getEmail());

        if(user.getAddress() != null){
            String addrs = user.getAddress().getStreet() +"," +
                    user.getAddress().getCity() + "," +
                    user.getAddress().getState() + "," +
                    user.getAddress().getCountry() + " - " +
                    user.getAddress().getZipcode();
            userDto.setAddress(addrs);
        }
        return userDto;
    }
}
