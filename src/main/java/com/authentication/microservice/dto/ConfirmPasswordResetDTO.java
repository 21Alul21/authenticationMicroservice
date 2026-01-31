package com.authentication.microservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmPasswordResetDTO(
    @NotBlank String resetToken,
    @NotBlank String newPassword
) {

}
