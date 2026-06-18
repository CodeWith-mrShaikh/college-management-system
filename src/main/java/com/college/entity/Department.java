package com.college.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "departments")
public class Department {
    @Id
    private String id;
    private String departmentName;
    private String hodName;
    private String location;

    public Department() {}

    public Department(String id, String departmentName, String hodName, String location) {
        this.id = id;
        this.departmentName = departmentName;
        this.hodName = hodName;
        this.location = location;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getHodName() { return hodName; }
    public void setHodName(String hodName) { this.hodName = hodName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
