package com.authentication.microservice.dto;

public record UserResponseDTO(
    String id,
    String firstName,
    String lastName,
    String email,
    boolean isActive
) {

}
