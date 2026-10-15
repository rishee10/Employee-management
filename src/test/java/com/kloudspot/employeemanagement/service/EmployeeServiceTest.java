package com.kloudspot.employeemanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kloudspot.employeemanagement.dto.EmployeeRequest;
import com.kloudspot.employeemanagement.dto.EmployeeResponse;
import com.kloudspot.employeemanagement.exception.DuplicateResourceException;
import com.kloudspot.employeemanagement.exception.ResourceNotFoundException;
import com.kloudspot.employeemanagement.model.Employee;
import com.kloudspot.employeemanagement.model.EmploymentStatus;
import com.kloudspot.employeemanagement.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeRequest request;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setEmployeeId("EMP2024-001");
        employee.setFullName("Priya Sharma");
        employee.setEmail("priya.sharma@kloudspot.com");
        employee.setPhoneNumber("+91-9876543210");
        employee.setDepartment("Engineering");
        employee.setDesignation("Senior Developer");
        employee.setDateOfJoining(LocalDate.of(2022, 3, 15));
        employee.setSalary(new BigDecimal("850000.00"));
        employee.setEmploymentStatus(EmploymentStatus.ACTIVE);

        request = new EmployeeRequest();
        request.setEmployeeId("EMP2024-001");
        request.setFullName("Priya Sharma");
        request.setEmail("priya.sharma@kloudspot.com");
        request.setPhoneNumber("+91-9876543210");
        request.setDepartment("Engineering");
        request.setDesignation("Senior Developer");
        request.setDateOfJoining(LocalDate.of(2022, 3, 15));
        request.setSalary(new BigDecimal("850000.00"));
        request.setEmploymentStatus(EmploymentStatus.ACTIVE);
    }

    @Test
    void getById_returnsEmployeeWhenFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getById(1L);

        assertThat(response.getFullName()).isEqualTo("Priya Sharma");
        assertThat(response.getEmail()).isEqualTo("priya.sharma@kloudspot.com");
    }

    @Test
    void getById_throwsWhenMissing() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_persistsNormalizedEmail() {
        when(employeeRepository.existsByEmailAndIdNot("priya.sharma@kloudspot.com", -1L)).thenReturn(false);
        when(employeeRepository.existsByEmployeeIdAndIdNot("EMP2024-001", -1L)).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        EmployeeResponse response = employeeService.create(request);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("priya.sharma@kloudspot.com");
        assertThat(response.getId()).isEqualTo(10L);
    }

    @Test
    void create_rejectsDuplicateEmail() {
        when(employeeRepository.existsByEmailAndIdNot("priya.sharma@kloudspot.com", -1L)).thenReturn(true);

        assertThatThrownBy(() -> employeeService.create(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Email");
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void update_appliesChanges() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmailAndIdNot("priya.sharma@kloudspot.com", 1L)).thenReturn(false);
        when(employeeRepository.existsByEmployeeIdAndIdNot("EMP2024-001", 1L)).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        request.setDesignation("Lead Developer");
        EmployeeResponse response = employeeService.update(1L, request);

        assertThat(response.getDesignation()).isEqualTo("Lead Developer");
    }

    @Test
    void delete_removesExistingEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.delete(1L);

        verify(employeeRepository).delete(employee);
    }

    @Test
    void listEmployees_delegatesToRepositorySearch() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> page = new PageImpl<>(java.util.List.of(employee));
        when(employeeRepository.searchEmployees("priya", "Engineering", EmploymentStatus.ACTIVE, pageable))
                .thenReturn(page);

        Page<EmployeeResponse> result =
                employeeService.listEmployees("priya", "Engineering", EmploymentStatus.ACTIVE, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(employeeRepository).searchEmployees(eq("priya"), eq("Engineering"), eq(EmploymentStatus.ACTIVE), eq(pageable));
    }
}
