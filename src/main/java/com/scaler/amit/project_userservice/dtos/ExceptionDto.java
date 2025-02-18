package com.scaler.amit.project_userservice.dtos;

import org.springframework.http.HttpStatus;

public class ExceptionDto {
    private HttpStatus errorCode;
    private String message;

    public ExceptionDto(HttpStatus errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
