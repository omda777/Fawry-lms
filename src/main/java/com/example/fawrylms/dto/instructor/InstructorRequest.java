package com.example.fawrylms.dto.instructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InstructorRequest(
        @NotBlank(message = "Instructor name is required") String name,
        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address") String email,
        @NotBlank(message = "Specialization is required") String specialization
) {
}
