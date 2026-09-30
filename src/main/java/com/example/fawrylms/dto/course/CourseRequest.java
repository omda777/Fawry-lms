package com.example.fawrylms.dto.course;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CourseRequest(
        @NotBlank(message = "Course title is required") String title,
        String description,
        @Min(value = 1, message = "Duration must be at least 1 hour") int durationHours,
        Long instructorId
) {
}
