package com.college.controller;

import com.college.dto.DepartmentDto;
import com.college.entity.Department;
import com.college.mapper.DtoMapper;
import com.college.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) { this.departmentService = departmentService; }

    @PostMapping
    public ResponseEntity<DepartmentDto> create(@Valid @RequestBody DepartmentDto dto) {
        Department saved = departmentService.addDepartment(DtoMapper.toDepartmentEntity(dto));
        DepartmentDto out = DtoMapper.toDepartmentDto(saved);
        return ResponseEntity.created(URI.create("/api/departments/" + out.getId())).body(out);
    }

    @GetMapping
    public List<DepartmentDto> list() { return departmentService.getAll().stream().map(DtoMapper::toDepartmentDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public DepartmentDto get(@PathVariable String id) { return DtoMapper.toDepartmentDto(departmentService.getDepartment(id)); }

    @PutMapping("/{id}")
    public DepartmentDto update(@PathVariable String id, @Valid @RequestBody DepartmentDto dto) {
        Department e = DtoMapper.toDepartmentEntity(dto);
        e.setId(id);
        return DtoMapper.toDepartmentDto(departmentService.updateDepartment(e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
