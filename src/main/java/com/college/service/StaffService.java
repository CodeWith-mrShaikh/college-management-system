package com.college.service;

import com.college.entity.Staff;
import com.college.exception.ResourceNotFoundException;
import com.college.repository.StaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final StaffRepository repo;
    private final Logger logger = LoggerFactory.getLogger(StaffService.class);

    public StaffService(StaffRepository repo) { this.repo = repo; }

    public Staff addStaff(Staff s) {
        Objects.requireNonNull(s, "Staff must not be null");
        Staff saved = repo.save(s);
        logger.info("Staff added: {}", saved.getId());
        return saved;
    }

    public Staff getStaff(String id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff not found with ID " + id)); }

    public List<Staff> getAll() { return repo.findAll(); }

    public Staff updateStaff(Staff s) {
        Objects.requireNonNull(s, "Staff must not be null");
        Objects.requireNonNull(s.getId(), "Staff id must not be null");
        if (!repo.existsById(s.getId())) throw new ResourceNotFoundException("Staff not found with ID " + s.getId());
        Staff updated = repo.save(s);
        logger.info("Staff updated: {}", updated.getId());
        return updated;
    }

    public void deleteStaff(String id) {
        Objects.requireNonNull(id, "Staff id must not be null");
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Staff not found with ID " + id);
        repo.deleteById(id);
        logger.info("Staff deleted: {}", id);
    }
}
