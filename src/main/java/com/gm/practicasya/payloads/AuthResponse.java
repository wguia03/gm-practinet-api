package com.gm.practicasya.payloads;

import java.util.Set;

//@JsonPropertyOrder({"jwt", "username", "message"})
public record AuthResponse(
        String jwt,
        Integer id,
        String username,
        Set<String> roles,
        String firstName,
        String lastName,
        String phoneNumber,
        String companyName,
        String companyRUC
) {
}
