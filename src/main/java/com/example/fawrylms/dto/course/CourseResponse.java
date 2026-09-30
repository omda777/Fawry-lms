package com.example.fawrylms.dto.course;

public record CourseResponse(
        Long id,
        String title,
        String description,
        int durationHours,
        Long instructorId,
        String instructorName
) {
}
