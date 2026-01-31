package com.authentication.microservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO(
    @NotBlank String oldPassword,
    @NotBlank String newPassword
) {

}