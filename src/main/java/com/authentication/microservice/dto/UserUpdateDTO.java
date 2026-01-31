package com.authentication.microservice.dto;

public class UserUpdateDTO {
    private String email;
    private String role;

    public UserUpdateDTO(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String email() {
        return email;
    }

    public String role() {
        return role;
    }
}