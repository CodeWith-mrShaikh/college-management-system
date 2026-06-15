package com.college.service;

import com.college.entity.Department;
import com.college.exception.ResourceNotFoundException;
import com.college.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository repo;
    private final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    public DepartmentService(DepartmentRepository repo) { this.repo = repo; }

    public Department addDepartment(Department d) {
        Objects.requireNonNull(d, "Department must not be null");
        Department saved = repo.save(d);
        logger.info("Department added: {}", saved.getId());
        return saved;
    }

    public Department getDepartment(String id) {
        Objects.requireNonNull(id, "Department id must not be null");
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with ID " + id));
    }

    public List<Department> getAll() { return repo.findAll(); }

    public Department updateDepartment(Department d) {
        Objects.requireNonNull(d, "Department must not be null");
        Objects.requireNonNull(d.getId(), "Department id must not be null");
        if (!repo.existsById(d.getId())) throw new ResourceNotFoundException("Department not found with ID " + d.getId());
        Department updated = repo.save(d);
        logger.info("Department updated: {}", updated.getId());
        return updated;
    }

    public void deleteDepartment(String id) {
        Objects.requireNonNull(id, "Department id must not be null");
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Department not found with ID " + id);
        repo.deleteById(id);
        logger.info("Department deleted: {}", id);
    }
}
