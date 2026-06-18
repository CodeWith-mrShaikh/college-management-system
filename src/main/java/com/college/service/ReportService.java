package com.college.service;

import com.college.mapper.DtoMapper;
import com.college.repository.BookRepository;
import com.college.repository.DepartmentRepository;
import com.college.repository.StaffRepository;
import com.college.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {
    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;
    private final BookRepository bookRepository;
    private final DepartmentRepository departmentRepository;

    public ReportService(StudentRepository studentRepository,
                         StaffRepository staffRepository,
                         BookRepository bookRepository,
                         DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.staffRepository = staffRepository;
        this.bookRepository = bookRepository;
        this.departmentRepository = departmentRepository;
    }

    public Map<String, Object> studentsReport() {
        var items = DtoMapper.toStudentDtoList(studentRepository.findAll());
        Map<String, Object> m = new HashMap<>();
        m.put("count", items.size());
        m.put("items", items);
        return m;
    }

    public Map<String, Object> staffReport() {
        var items = DtoMapper.toStaffDtoList(staffRepository.findAll());
        Map<String, Object> m = new HashMap<>();
        m.put("count", items.size());
        m.put("items", items);
        return m;
    }

    public Map<String, Object> booksReport() {
        var items = DtoMapper.toBookDtoList(bookRepository.findAll());
        Map<String, Object> m = new HashMap<>();
        m.put("count", items.size());
        m.put("items", items);
        return m;
    }

    public Map<String, Object> departmentsReport() {
        var items = DtoMapper.toDepartmentDtoList(departmentRepository.findAll());
        Map<String, Object> m = new HashMap<>();
        m.put("count", items.size());
        m.put("items", items);
        return m;
    }
}
