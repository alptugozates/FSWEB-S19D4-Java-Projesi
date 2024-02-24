package com.workintech.repository;

import com.workintech.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmail(String email);

    List<Employee> findBySalaryGreaterThanOrderBySalaryDesc(double salary);

    List<Employee> findByOrderByLastName();
}
