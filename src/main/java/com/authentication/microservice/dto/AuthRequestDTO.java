package com.authentication.microservice.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthRequestDTO(
    @NotEmpty String email,
    @NotEmpty String password
) {

}