
package com.authentication.microservice.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.authentication.microservice.dto.RegistrationRequestDTO;
import com.authentication.microservice.dto.RegistrationResponseDTO;
import com.authentication.microservice.auth.service.AuthService;
import com.authentication.microservice.dto.AuthRequestDTO;
import com.authentication.microservice.dto.ChangePasswordDTO;
import com.authentication.microservice.dto.ConfirmPasswordResetDTO;
import com.authentication.microservice.dto.EmailDTO;
import com.authentication.microservice.dto.LoginResponseDTO;


@RestController
@RequestMapping("api/v1/auth/")
public class AuthController{

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // Registration
    @PostMapping("register")
    public ResponseEntity<RegistrationResponseDTO> registerUser(@Valid @RequestBody RegistrationRequestDTO req){
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(authService.registerUser(req));
    }


    // Login
    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody AuthRequestDTO req){

        return ResponseEntity.ok(authService.loginUser(req));
    }


    // change password
    @PostMapping("user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO){
        authService.changePassword(changePasswordDTO);
        return ResponseEntity
        .noContent()
        .build();
    }


    // reset password
    @PostMapping("user/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid EmailDTO emailDTO){
        authService.resetPassword(emailDTO);
        return ResponseEntity
                .noContent()
                .build();
    }


    // confirm password reset
    @PostMapping("user/confirm-password-reset")
    public void confirmPasswordReset(@Valid @RequestBody ConfirmPasswordResetDTO confirmPasswordResetDTO){
        // recieve DTO containing reset token and new password, change user password in db and delete the reset token in db
        authService.confirmPasswordReset(confirmPasswordResetDTO);
    }


    // refresh endpoint

    // OAuth Authorization

}