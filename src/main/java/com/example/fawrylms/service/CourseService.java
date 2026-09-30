package com.example.fawrylms.service;

import com.example.fawrylms.dto.course.CourseRequest;
import com.example.fawrylms.dto.course.CourseResponse;
import com.example.fawrylms.dto.student.StudentResponse;
import com.example.fawrylms.entity.Course;
import com.example.fawrylms.entity.Instructor;
import com.example.fawrylms.entity.Student;
import com.example.fawrylms.repository.CourseRepository;
import com.example.fawrylms.repository.InstructorRepository;
import com.example.fawrylms.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository,
                         InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CourseResponse getById(Long id) {
        return toResponse(findCourse(id));
    }

    public CourseResponse create(CourseRequest request) {
        Course course = new Course();
        copyRequest(request, course);
        return toResponse(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse update(Long id, CourseRequest request) {
        Course course = findCourse(id);
        copyRequest(request, course);
        return toResponse(course);
    }

    public void delete(Long id) {
        courseRepository.delete(findCourse(id));
    }

    @Transactional
    public CourseResponse assignInstructor(Long courseId, Long instructorId) {
        Course course = findCourse(courseId);
        course.setInstructor(findInstructor(instructorId));
        return toResponse(course);
    }

    @Transactional
    public CourseResponse enrollStudent(Long courseId, Long studentId) {
        Course course = findCourse(courseId);
        Student student = findStudent(studentId);
        course.getStudents().add(student);
        return toResponse(course);
    }

    @Transactional
    public CourseResponse removeStudent(Long courseId, Long studentId) {
        Course course = findCourse(courseId);
        if (course.getStudents().removeIf(student -> student.getId().equals(studentId))) {
            return toResponse(course);
        }
        throw new IllegalArgumentException("Student is not enrolled in this course");
    }

    @Transactional
    public List<StudentResponse> getEnrolledStudents(Long courseId) {
        return findCourse(courseId).getStudents().stream()
                .map(student -> new StudentResponse(student.getId(), student.getName(), student.getEmail()))
                .toList();
    }

    private Course findCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow();
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow();
    }

    private Instructor findInstructor(Long id) {
        return instructorRepository.findById(id).orElseThrow();
    }

    private void copyRequest(CourseRequest request, Course course) {
        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setDurationHours(request.durationHours());
        course.setInstructor(request.instructorId() == null ? null : findInstructor(request.instructorId()));
    }

    private CourseResponse toResponse(Course course) {
        Instructor instructor = course.getInstructor();
        return new CourseResponse(
                course.getId(), course.getTitle(), course.getDescription(), course.getDurationHours(),
                instructor == null ? null : instructor.getId(),
                instructor == null ? null : instructor.getName()
        );
    }
}
