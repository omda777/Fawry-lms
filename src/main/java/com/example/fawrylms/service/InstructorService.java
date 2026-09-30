package com.example.fawrylms.service;

import com.example.fawrylms.dto.instructor.InstructorRequest;
import com.example.fawrylms.dto.instructor.InstructorResponse;
import com.example.fawrylms.entity.Course;
import com.example.fawrylms.entity.Instructor;
import com.example.fawrylms.repository.InstructorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<InstructorResponse> getAll() {
        return instructorRepository.findAll().stream().map(this::toResponse).toList();
    }

    public InstructorResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    public InstructorResponse create(InstructorRequest request) {
        if (instructorRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("An instructor already uses this email");
        }
        Instructor instructor = new Instructor();
        copyRequest(request, instructor);
        return toResponse(instructorRepository.save(instructor));
    }

    @Transactional
    public InstructorResponse update(Long id, InstructorRequest request) {
        if (instructorRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new IllegalArgumentException("An instructor already uses this email");
        }
        Instructor instructor = findEntity(id);
        copyRequest(request, instructor);
        return toResponse(instructor);
    }

    @Transactional
    public void delete(Long id) {
        Instructor instructor = findEntity(id);
        for (Course course : List.copyOf(instructor.getCourses())) {
            course.setInstructor(null);
        }
        instructorRepository.delete(instructor);
    }

    private Instructor findEntity(Long id) {
        return instructorRepository.findById(id).orElseThrow();
    }

    private void copyRequest(InstructorRequest request, Instructor instructor) {
        instructor.setName(request.name());
        instructor.setEmail(request.email());
        instructor.setSpecialization(request.specialization());
    }

    private InstructorResponse toResponse(Instructor instructor) {
        return new InstructorResponse(
                instructor.getId(), instructor.getName(), instructor.getEmail(), instructor.getSpecialization()
        );
    }
}
