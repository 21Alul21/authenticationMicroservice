package com.authentication.microservice.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generalExceptions(Exception ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    Map.of("message", "an error occured, please try again later")
                );
    }


     @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<?> accountNotActiveException(AccountNotActiveException ex){
        if(ex.getMessage() != null){
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    Map.of("message", ex.getMessage())
                );
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    Map.of("message", "your account is deactivated, contact the admin")
                );
    }

}