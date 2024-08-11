package com.example.demo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Testy dla ReactiveSpringStudentApiController
 * Wymaga uruchomionego springa ReactiveSpringApplication
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReactiveSpringStudentApiControllerTests {

    Logger logger = LoggerFactory.getLogger(StudentApiController.class);

    private final StudentApiController studentApiController = new StudentApiController();

    @Test
    @Order(1)
    void testControllerGet() {
        Flux<Student> studentFlux = studentApiController.get();
        studentFlux
                .doOnNext(student -> logger.info(student.toString()))
                .subscribe();
        StepVerifier.create(studentFlux)
                .expectNext(new Student(1, "John Doe", "25"))
                .expectNext(new Student(2, "Jane Doe", "22"))
                .expectNext(new Student(3, "John Smith", "30"))
                .verifyComplete();
    }

    @Test()
    @Order(2)
    void testControllerCreateStudent() {
        Mono<Student> studentMono = studentApiController.createStudent(new Student(4, "Paweł", "30"));
        studentMono
                .doOnNext(student -> logger.info(student.toString()))
                .subscribe();
        StepVerifier.create(studentMono)
                .expectNext(new Student(4, "Paweł", "30"))
                .verifyComplete();
    }
}
