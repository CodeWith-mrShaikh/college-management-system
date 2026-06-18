package com.college.controller;

import com.college.entity.Student;
import com.college.service.StudentService;
import com.college.dto.StudentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private StudentService studentService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void createAndGetStudent() throws Exception {
        Student s = new Student("1","Alice","alice@example.com","12345","dept1");
        Mockito.when(studentService.addStudent(any(Student.class))).thenReturn(s);
        Mockito.when(studentService.getStudent("1")).thenReturn(s);

        StudentDto dto = new StudentDto();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setPhone("12345");
        dto.setDepartmentId("dept1");

        mockMvc.perform(post("/api/students").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.name").value("Alice"));

        mockMvc.perform(get("/api/students/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"));
    }
}
