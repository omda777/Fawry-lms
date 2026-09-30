# Basic LMS API

A minimal Learning Management System built with Spring Boot, Spring Data JPA, and an in-memory H2 database.

## Project structure

```
entity/       JPA database tables: Student, Course, and Instructor
dto/          Request and response objects used by the API
repository/   Spring Data JPA database access
service/      Business logic, mapping entities to DTOs
controller/   REST endpoints
common/       Exception handling
```

Controllers never expose entities directly. They accept `*Request` DTOs and return `*Response` DTOs.

## Features

1. Student CRUD: create, list, view, update, and delete students.
2. Course CRUD: create, list, view, update, and delete courses.
3. Enrollment: enroll a student in a course, list enrolled students, or remove an enrollment.
4. Instructor CRUD: create, list, view, update, and delete instructors; assign an instructor to a course.

## Run

```bash
./mvnw spring-boot:run
```

The API runs at `http://localhost:8080`. The database console is available at `http://localhost:8080/h2-console` with JDBC URL `jdbc:h2:mem:lmsdb`, username `sa`, and a blank password.

## Main endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET / POST | `/api/students` | List or create students |
| GET / PUT / DELETE | `/api/students/{id}` | Read, update, or delete a student |
| GET / POST | `/api/courses` | List or create courses |
| GET / PUT / DELETE | `/api/courses/{id}` | Read, update, or delete a course |
| PUT | `/api/courses/{courseId}/instructor/{instructorId}` | Assign an instructor to a course |
| POST | `/api/courses/{courseId}/students/{studentId}` | Enroll a student |
| GET | `/api/courses/{courseId}/students` | List enrolled students |
| DELETE | `/api/courses/{courseId}/students/{studentId}` | Remove enrollment |
| GET / POST | `/api/instructors` | List or create instructors |
| GET / PUT / DELETE | `/api/instructors/{id}` | Read, update, or delete an instructor |

### Example requests

Create a student:

```json
POST /api/students
{
  "name": "Sara Ahmed",
  "email": "sara@example.com"
}
```

Create a course:

```json
POST /api/courses
{
  "title": "Java Basics",
  "description": "Introduction to Java programming",
  "durationHours": 12,
  "instructorId": 1
}
```

Create an instructor:

```json
POST /api/instructors
{
  "name": "Omar Ali",
  "email": "omar@example.com",
  "specialization": "Java"
}
```
# Fawry-lms
