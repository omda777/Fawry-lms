package com.example.fawrylms.controller;

import com.example.fawrylms.dto.instructor.InstructorRequest;
import com.example.fawrylms.dto.instructor.InstructorResponse;
import com.example.fawrylms.service.InstructorService;
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
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public List<InstructorResponse> getAll() { return instructorService.getAll(); }

    @GetMapping("/{id}")
    public InstructorResponse getById(@PathVariable Long id) { return instructorService.getById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstructorResponse create(@Valid @RequestBody InstructorRequest request) {
        return instructorService.create(request);
    }

    @PutMapping("/{id}")
    public InstructorResponse update(@PathVariable Long id, @Valid @RequestBody InstructorRequest request) {
        return instructorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { instructorService.delete(id); }
}
