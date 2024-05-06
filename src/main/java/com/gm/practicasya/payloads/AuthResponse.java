package com.gm.practicasya.payloads;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gm.practicasya.entities.Role;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//@JsonPropertyOrder({"jwt", "username", "message"})
public record AuthResponse(
        String jwt,
        Integer id,
        String username,
        Set<Role> roles,
        String firstName,
        String lastName,
        String phoneNumber,
        String companyName,
        String companyRUC
) {
}
