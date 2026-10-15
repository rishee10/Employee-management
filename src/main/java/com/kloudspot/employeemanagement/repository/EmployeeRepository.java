package com.kloudspot.employeemanagement.repository;

import com.kloudspot.employeemanagement.model.Employee;
import com.kloudspot.employeemanagement.model.EmploymentStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeId(String employeeId);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmployeeIdAndIdNot(String employeeId, Long id);

    @Query("""
            SELECT e FROM Employee e
            WHERE (:search IS NULL OR :search = '' OR
                   LOWER(e.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                   LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:department IS NULL OR :department = '' OR e.department = :department)
              AND (:status IS NULL OR e.employmentStatus = :status)
            """)
    Page<Employee> searchEmployees(
            @Param("search") String search,
            @Param("department") String department,
            @Param("status") EmploymentStatus status,
            Pageable pageable);

    @Query("SELECT DISTINCT e.department FROM Employee e ORDER BY e.department")
    java.util.List<String> findDistinctDepartments();
}
