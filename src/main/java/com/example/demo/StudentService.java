package com.example.demo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {
    private Flux<Student> studentFlux;

    public StudentService() {
        studentFlux = Flux.just(
                new Student(1, "John Doe", "25"),
                new Student(2, "Jane Doe", "22"),
                new Student(3, "John Smith", "30")
        );
    }

    public Flux<Student> getStudents() {
        return this.studentFlux;
    }

    public Mono<Student> addStudent(Student student) {
        studentFlux = this.studentFlux.mergeWith(Mono.just(student));
        return Mono.just(student);
    }
}
