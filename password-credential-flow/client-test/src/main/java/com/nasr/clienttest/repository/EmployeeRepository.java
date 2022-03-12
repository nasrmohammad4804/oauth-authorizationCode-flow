package com.nasr.clienttest.repository;

import com.nasr.clienttest.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findEmployeeByUsername(String username);
}
