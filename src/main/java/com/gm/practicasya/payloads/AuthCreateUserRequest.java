package com.gm.practicasya.payloads;

import com.gm.practicasya.entities.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AuthCreateUserRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        String phoneNumber,
        String companyName,
        String companyRUC,
        List<String> roleList) {
}
