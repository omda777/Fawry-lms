package com.example.fawrylms.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record StudentRequest(
        @NotBlank(message = "Student name is required") String name,
        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address") String email
) {
}
