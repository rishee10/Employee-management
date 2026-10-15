package com.kloudspot.employeemanagement.dto;

import com.kloudspot.employeemanagement.model.Employee;
import com.kloudspot.employeemanagement.model.EmploymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponse {

    private Long id;
    private String employeeId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String department;
    private String designation;
    private LocalDate dateOfJoining;
    private BigDecimal salary;
    private EmploymentStatus employmentStatus;

    public static EmployeeResponse from(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.id = employee.getId();
        response.employeeId = employee.getEmployeeId();
        response.fullName = employee.getFullName();
        response.email = employee.getEmail();
        response.phoneNumber = employee.getPhoneNumber();
        response.department = employee.getDepartment();
        response.designation = employee.getDesignation();
        response.dateOfJoining = employee.getDateOfJoining();
        response.salary = employee.getSalary();
        response.employmentStatus = employee.getEmploymentStatus();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }
}
