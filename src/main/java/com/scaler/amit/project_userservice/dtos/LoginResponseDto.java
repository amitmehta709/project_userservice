package com.scaler.amit.project_userservice.dtos;

import com.scaler.amit.project_userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;

    public static LoginResponseDto fromToken(Token token) {
        LoginResponseDto ldto = new LoginResponseDto();
        ldto.setToken(token.getTokenValue());
        return ldto;
    }
}
