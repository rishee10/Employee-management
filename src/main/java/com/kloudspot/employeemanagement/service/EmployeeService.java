package com.kloudspot.employeemanagement.service;

import com.kloudspot.employeemanagement.dto.EmployeeRequest;
import com.kloudspot.employeemanagement.dto.EmployeeResponse;
import com.kloudspot.employeemanagement.exception.DuplicateResourceException;
import com.kloudspot.employeemanagement.exception.ResourceNotFoundException;
import com.kloudspot.employeemanagement.model.Employee;
import com.kloudspot.employeemanagement.model.EmploymentStatus;
import com.kloudspot.employeemanagement.repository.EmployeeRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> listEmployees(
            String search, String department, EmploymentStatus status, Pageable pageable) {
        return employeeRepository
                .searchEmployees(normalize(search), normalize(department), status, pageable)
                .map(EmployeeResponse::from);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getById(Long id) {
        return EmployeeResponse.from(findEmployee(id));
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        validateUniqueFields(null, request);
        Employee employee = mapToEntity(new Employee(), request);
        return EmployeeResponse.from(employeeRepository.save(employee));
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = findEmployee(id);
        validateUniqueFields(id, request);
        mapToEntity(employee, request);
        return EmployeeResponse.from(employeeRepository.save(employee));
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = findEmployee(id);
        employeeRepository.delete(employee);
    }

    @Transactional(readOnly = true)
    public List<String> listDepartments() {
        return employeeRepository.findDistinctDepartments();
    }

    private Employee findEmployee(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    private void validateUniqueFields(Long id, EmployeeRequest request) {
        Long safeId = id == null ? -1L : id;
        if (employeeRepository.existsByEmailAndIdNot(request.getEmail(), safeId)) {
            throw new DuplicateResourceException("Email already in use: " + request.getEmail());
        }
        if (request.getEmployeeId() != null && !request.getEmployeeId().isBlank()
                && employeeRepository.existsByEmployeeIdAndIdNot(request.getEmployeeId(), safeId)) {
            throw new DuplicateResourceException("Employee ID already exists: " + request.getEmployeeId());
        }
    }

    private Employee mapToEntity(Employee employee, EmployeeRequest request) {
        if (request.getEmployeeId() != null && !request.getEmployeeId().isBlank()) {
            employee.setEmployeeId(request.getEmployeeId().trim());
        }
        employee.setFullName(request.getFullName().trim());
        employee.setEmail(request.getEmail().trim().toLowerCase());
        employee.setPhoneNumber(request.getPhoneNumber().trim());
        employee.setDepartment(request.getDepartment().trim());
        employee.setDesignation(request.getDesignation().trim());
        employee.setDateOfJoining(request.getDateOfJoining());
        employee.setSalary(request.getSalary());
        employee.setEmploymentStatus(request.getEmploymentStatus());
        return employee;
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
