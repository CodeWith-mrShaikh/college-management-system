package com.college.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class StudentDto {
    private String id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    private String phone;
    private String departmentId;
    private List<String> borrowedBookIds;

    public StudentDto() {}

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
