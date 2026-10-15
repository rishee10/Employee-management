package com.kloudspot.employeemanagement.controller;

import com.kloudspot.employeemanagement.model.EmploymentStatus;
import com.kloudspot.employeemanagement.service.EmployeeExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeExportController {

    private final EmployeeExportService exportService;

    public EmployeeExportController(EmployeeExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public ResponseEntity<String> exportCsv(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) EmploymentStatus status) {
        String csv = exportService.exportCsv(search, department, status);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }
}
