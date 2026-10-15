package com.kloudspot.employeemanagement.service;

import com.kloudspot.employeemanagement.dto.EmployeeResponse;
import com.kloudspot.employeemanagement.model.EmploymentStatus;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeExportService {

    private static final int EXPORT_PAGE_SIZE = 500;

    private final EmployeeService employeeService;

    public EmployeeExportService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Transactional(readOnly = true)
    public String exportCsv(String search, String department, EmploymentStatus status) {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee ID,Full Name,Email,Phone,Department,Designation,Date of Joining,Salary,Status\n");

        int page = 0;
        List<EmployeeResponse> batch;
        do {
            batch = employeeService
                    .listEmployees(search, department, status, PageRequest.of(page, EXPORT_PAGE_SIZE, Sort.by("fullName")))
                    .getContent();
            for (EmployeeResponse e : batch) {
                sb.append(csv(e.getEmployeeId()))
                        .append(',')
                        .append(csv(e.getFullName()))
                        .append(',')
                        .append(csv(e.getEmail()))
                        .append(',')
                        .append(csv(e.getPhoneNumber()))
                        .append(',')
                        .append(csv(e.getDepartment()))
                        .append(',')
                        .append(csv(e.getDesignation()))
                        .append(',')
                        .append(e.getDateOfJoining())
                        .append(',')
                        .append(e.getSalary())
                        .append(',')
                        .append(e.getEmploymentStatus())
                        .append('\n');
            }
            page++;
        } while (batch.size() == EXPORT_PAGE_SIZE);

        return sb.toString();
    }

    private String csv(String value) {
        if (value == null) {
            return "";
        }
        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}
