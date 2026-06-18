package com.college.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "staff")
public class Staff {
    @Id
    private String id;
    private String name;
    private String designation;
    private String email;
    private String departmentId;

    public Staff() {}

    public Staff(String id, String name, String designation, String email, String departmentId) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.email = email;
        this.departmentId = departmentId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
}
