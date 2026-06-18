package com.college.mapper;

import com.college.dto.*;
import com.college.entity.*;
import java.util.List;
import java.util.stream.Collectors;

public final class DtoMapper {
    private DtoMapper() {}

    public static StudentDto toStudentDto(Student s) {
        if (s == null) return null;
        StudentDto d = new StudentDto();
        d.setId(s.getId());
        d.setName(s.getName());
        d.setEmail(s.getEmail());
        d.setPhone(s.getPhone());
        d.setDepartmentId(s.getDepartmentId());
        d.setBorrowedBookIds(s.getBorrowedBookIds());
        return d;
    }

    public static Student toStudentEntity(StudentDto d) {
        if (d == null) return null;
        Student s = new Student();
        s.setId(d.getId());
        s.setName(d.getName());
        s.setEmail(d.getEmail());
        s.setPhone(d.getPhone());
        s.setDepartmentId(d.getDepartmentId());
        s.setBorrowedBookIds(d.getBorrowedBookIds());
        return s;
    }

    public static StaffDto toStaffDto(Staff s) {
        if (s == null) return null;
        StaffDto d = new StaffDto();
        d.setId(s.getId());
        d.setName(s.getName());
        d.setDesignation(s.getDesignation());
        d.setEmail(s.getEmail());
        d.setDepartmentId(s.getDepartmentId());
        return d;
    }

    public static Staff toStaffEntity(StaffDto d) {
        if (d == null) return null;
        Staff s = new Staff();
        s.setId(d.getId());
        s.setName(d.getName());
        s.setDesignation(d.getDesignation());
        s.setEmail(d.getEmail());
        s.setDepartmentId(d.getDepartmentId());
        return s;
    }

    public static DepartmentDto toDepartmentDto(Department p) {
        if (p == null) return null;
        DepartmentDto d = new DepartmentDto();
        d.setId(p.getId());
        d.setDepartmentName(p.getDepartmentName());
        d.setHodName(p.getHodName());
        d.setLocation(p.getLocation());
        return d;
    }

    public static Department toDepartmentEntity(DepartmentDto d) {
        if (d == null) return null;
        Department p = new Department();
        p.setId(d.getId());
        p.setDepartmentName(d.getDepartmentName());
        p.setHodName(d.getHodName());
        p.setLocation(d.getLocation());
        return p;
    }

    public static BookDto toBookDto(Book b) {
        if (b == null) return null;
        BookDto d = new BookDto();
        d.setId(b.getId());
        d.setTitle(b.getTitle());
        d.setAuthor(b.getAuthor());
        d.setIsbn(b.getIsbn());
        return d;
    }

    public static Book toBookEntity(BookDto d) {
        if (d == null) return null;
        Book b = new Book();
        b.setId(d.getId());
        b.setTitle(d.getTitle());
        b.setAuthor(d.getAuthor());
        b.setIsbn(d.getIsbn());
        return b;
    }

    public static List<StudentDto> toStudentDtoList(List<Student> list) {
        return list == null ? List.of() : list.stream().map(DtoMapper::toStudentDto).collect(Collectors.toList());
    }
    public static List<StaffDto> toStaffDtoList(List<Staff> list) {
        return list == null ? List.of() : list.stream().map(DtoMapper::toStaffDto).collect(Collectors.toList());
    }
    public static List<BookDto> toBookDtoList(List<Book> list) {
        return list == null ? List.of() : list.stream().map(DtoMapper::toBookDto).collect(Collectors.toList());
    }
    public static List<DepartmentDto> toDepartmentDtoList(List<Department> list) {
        return list == null ? List.of() : list.stream().map(DtoMapper::toDepartmentDto).collect(Collectors.toList());
    }
}
