package com.college.integration;

import com.college.entity.Student;
import com.college.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class MongoIntegrationTest {
    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:6.0.6");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @Autowired
    StudentRepository studentRepository;

    @Test
    public void repositorySavesAndFinds() {
        Student s = new Student(null, "Integration Test", "it@example.com", "000", "dept-it");
        Student saved = studentRepository.save(s);
        assertThat(saved.getId()).isNotNull();
        assertThat(studentRepository.findById(saved.getId())).isPresent();
    }
}
