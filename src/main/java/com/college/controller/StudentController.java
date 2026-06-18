package com.college.controller;

import com.college.dto.StudentDto;
import com.college.entity.Student;
import com.college.mapper.DtoMapper;
import com.college.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentDto dto) {
        Student saved = studentService.addStudent(DtoMapper.toStudentEntity(dto));
        StudentDto out = DtoMapper.toStudentDto(saved);
        return ResponseEntity.created(URI.create("/api/students/" + out.getId())).body(out);
    }

    @GetMapping
    public List<StudentDto> list() {
        return studentService.getAll().stream().map(DtoMapper::toStudentDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StudentDto get(@PathVariable String id) {
        Student s = studentService.getStudent(id);
        return DtoMapper.toStudentDto(s);
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable String id, @Valid @RequestBody StudentDto dto) {
        Student e = DtoMapper.toStudentEntity(dto);
        e.setId(id);
        Student updated = studentService.updateStudent(e);
        return DtoMapper.toStudentDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
