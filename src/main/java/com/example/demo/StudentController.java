package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Student> addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }
}
