package com.example.fawrylms.service;

import com.example.fawrylms.dto.student.StudentRequest;
import com.example.fawrylms.dto.student.StudentResponse;
import com.example.fawrylms.entity.Course;
import com.example.fawrylms.entity.Student;
import com.example.fawrylms.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public StudentResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    public StudentResponse create(StudentRequest request) {
        if (studentRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("A student already uses this email");
        }
        Student student = new Student();
        copyRequest(request, student);
        return toResponse(studentRepository.save(student));
    }

    @Transactional
    public StudentResponse update(Long id, StudentRequest request) {
        if (studentRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new IllegalArgumentException("A student already uses this email");
        }
        Student student = findEntity(id);
        copyRequest(request, student);
        return toResponse(student);
    }

    @Transactional
    public void delete(Long id) {
        Student student = findEntity(id);
        for (Course course : List.copyOf(student.getCourses())) {
            course.getStudents().remove(student);
        }
        studentRepository.delete(student);
    }

    private Student findEntity(Long id) {
        return studentRepository.findById(id)
                .orElseThrow();
    }

    private void copyRequest(StudentRequest request, Student student) {
        student.setName(request.name());
        student.setEmail(request.email());
    }

    private StudentResponse toResponse(Student student) {
        return new StudentResponse(student.getId(), student.getName(), student.getEmail());
    }
}
