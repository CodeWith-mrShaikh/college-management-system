package com.college.controller;

import com.college.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) { this.reportService = reportService; }

    @GetMapping("/students")
    public ResponseEntity<Map<String,Object>> students() { return ResponseEntity.ok(reportService.studentsReport()); }

    @GetMapping("/staff")
    public ResponseEntity<Map<String,Object>> staff() { return ResponseEntity.ok(reportService.staffReport()); }

    @GetMapping("/books")
    public ResponseEntity<Map<String,Object>> books() { return ResponseEntity.ok(reportService.booksReport()); }

    @GetMapping("/departments")
    public ResponseEntity<Map<String,Object>> departments() { return ResponseEntity.ok(reportService.departmentsReport()); }
}
