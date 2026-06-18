package com.college.service;

import com.college.entity.Student;
import com.college.exception.ResourceNotFoundException;
import com.college.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student addStudent(Student s) {
        Objects.requireNonNull(s, "Student must not be null");
        Student saved = repo.save(s);
        logger.info("Student added: {}", saved.getId());
        return saved;
    }

    public Student getStudent(String id) {
        Objects.requireNonNull(id, "Student id must not be null");
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with ID " + id));
    }

    public List<Student> getAll() { return repo.findAll(); }

    public Student updateStudent(Student s) {
        Objects.requireNonNull(s, "Student must not be null");
        Objects.requireNonNull(s.getId(), "Student id must not be null");
        if (!repo.existsById(s.getId())) throw new ResourceNotFoundException("Student not found with ID " + s.getId());
        Student updated = repo.save(s);
        logger.info("Student updated: {}", updated.getId());
        return updated;
    }

    public void deleteStudent(String id) {
        Objects.requireNonNull(id, "Student id must not be null");
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Student not found with ID " + id);
        repo.deleteById(id);
        logger.info("Student deleted: {}", id);
    }
}
