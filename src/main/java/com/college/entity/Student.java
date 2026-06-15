package com.college.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "students")
public class Student {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String departmentId;
    private List<String> borrowedBookIds = new ArrayList<>();

    public Student() {}

    public Student(String id, String name, String email, String phone, String departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    public List<String> getBorrowedBookIds() { return borrowedBookIds; }
    public void setBorrowedBookIds(List<String> borrowedBookIds) { this.borrowedBookIds = borrowedBookIds; }
}
