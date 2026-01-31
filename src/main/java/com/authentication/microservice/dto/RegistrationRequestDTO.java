package com.authentication.microservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegistrationRequestDTO(
    @NotEmpty @Email String email,
    @NotEmpty String password,
    String firstName,
    String lastName,
    String specialCode
) {

}
