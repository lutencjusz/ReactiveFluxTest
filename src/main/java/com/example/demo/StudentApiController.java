package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/studentsApi")
public class StudentApiController {

    Logger logger = LoggerFactory.getLogger(StudentApiController.class);

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @EventListener(ApplicationReadyEvent.class)
    public Flux<Student> get() {
        Flux<Student> studentFlux = WebClient.create()
                .get()
                .uri("http://localhost:8080/students")
                .retrieve()
                .bodyToFlux(Student.class);
        studentFlux.subscribe(student -> logger.info(student.toString()));
        return studentFlux;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Student> createStudent(@RequestBody Student student) {
        Mono<Student> studentMono = WebClient.create()
                .post()
                .uri("http://localhost:8080/students")
                .body(Mono.just(student), Student.class)
                .retrieve()
                .bodyToMono(Student.class);
        studentMono.subscribe(s -> logger.info(s.toString()));
        return studentMono;
    }
}
