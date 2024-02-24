package com.workintech;

import com.workintech.entity.Employee;
import com.workintech.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByEmail() {
        List<Employee> employees = employeeRepository.findByEmail("test@example.com");
        assertEquals(0, employees.size());
    }

    @Test
    public void testFindBySalaryGreaterThanOrderBySalaryDesc() {
        List<Employee> employees = employeeRepository.findBySalaryGreaterThanOrderBySalaryDesc(50000.0);
        assertEquals(0, employees.size());
    }

    @Test
    public void testFindByOrderByLastName() {
        List<Employee> employees = employeeRepository.findByOrderByLastName();
        assertEquals(0, employees.size());
    }
}
