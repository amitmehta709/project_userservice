package com.scaler.amit.project_userservice.advices;

import com.scaler.amit.project_userservice.dtos.ExceptionDto;
import com.scaler.amit.project_userservice.exceptions.DuplicateRecordsException;
import com.scaler.amit.project_userservice.exceptions.InvalidDataException;
import com.scaler.amit.project_userservice.exceptions.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException ex){
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        //ex.printStackTrace();
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ExceptionDto> handleRuntimeException(AccessDeniedException ex){
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateRecordsException.class)
    ResponseEntity<ExceptionDto> handleDuplicateRecordsException(DuplicateRecordsException ex){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ExceptionDto> handleBadCredentialsException(BadCredentialsException ex){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.UNAUTHORIZED, ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<ExceptionDto> handleUserNameNotFoundException(UsernameNotFoundException ex){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    ResponseEntity<ExceptionDto> handleInvalidPasswordException(InvalidPasswordException ex){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataException.class)
    ResponseEntity<ExceptionDto> handleInvalidDataException(InvalidDataException ex){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
