package com.college.controller;

import com.college.dto.StaffDto;
import com.college.entity.Staff;
import com.college.mapper.DtoMapper;
import com.college.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffService staffService;
    public StaffController(StaffService staffService) { this.staffService = staffService; }

    @PostMapping
    public ResponseEntity<StaffDto> create(@Valid @RequestBody StaffDto dto) {
        Staff saved = staffService.addStaff(DtoMapper.toStaffEntity(dto));
        StaffDto out = DtoMapper.toStaffDto(saved);
        return ResponseEntity.created(URI.create("/api/staff/" + out.getId())).body(out);
    }

    @GetMapping
    public List<StaffDto> list() { return staffService.getAll().stream().map(DtoMapper::toStaffDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public StaffDto get(@PathVariable String id) { return DtoMapper.toStaffDto(staffService.getStaff(id)); }

    @PutMapping("/{id}")
    public StaffDto update(@PathVariable String id, @Valid @RequestBody StaffDto dto) {
        Staff e = DtoMapper.toStaffEntity(dto);
        e.setId(id);
        return DtoMapper.toStaffDto(staffService.updateStaff(e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
