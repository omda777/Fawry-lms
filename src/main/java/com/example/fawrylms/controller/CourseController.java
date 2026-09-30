package com.example.fawrylms.controller;

import com.example.fawrylms.dto.course.CourseRequest;
import com.example.fawrylms.dto.course.CourseResponse;
import com.example.fawrylms.dto.student.StudentResponse;
import com.example.fawrylms.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponse> getAll() { return courseService.getAll(); }

    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable Long id) { return courseService.getById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@Valid @RequestBody CourseRequest request) {
        return courseService.create(request);
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { courseService.delete(id); }

    @PutMapping("/{courseId}/instructor/{instructorId}")
    public CourseResponse assignInstructor(@PathVariable Long courseId, @PathVariable Long instructorId) {
        return courseService.assignInstructor(courseId, instructorId);
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public CourseResponse enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        return courseService.enrollStudent(courseId, studentId);
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public CourseResponse removeStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        return courseService.removeStudent(courseId, studentId);
    }

    @GetMapping("/{courseId}/students")
    public List<StudentResponse> getEnrolledStudents(@PathVariable Long courseId) {
        return courseService.getEnrolledStudents(courseId);
    }
}
