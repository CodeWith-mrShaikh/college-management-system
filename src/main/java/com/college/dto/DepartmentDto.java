package com.college.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentDto {
    private String id;

    @NotBlank(message = "departmentName is required")
    private String departmentName;

    private String hodName;
    private String location;

    public DepartmentDto() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getHodName() { return hodName; }
    public void setHodName(String hodName) { this.hodName = hodName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
