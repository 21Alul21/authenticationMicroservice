package com.authentication.microservice.dto;

public record LoginResponseDTO(
    String status,
    String message,
    String accessToken,
    String refreshToken,
    String role
) {

}