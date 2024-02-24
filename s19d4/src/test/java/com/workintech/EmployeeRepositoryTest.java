package com.workintech;

import com.workintech.entity.Employee;
import com.workintech.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    void testFindByEmail() {
        Employee testEmployee = new Employee();
        testEmployee.setEmail("test@example.com");
        employeeRepository.save(testEmployee);

        Optional<Employee> foundEmployee = employeeRepository.findByEmail("test@example.com");

        assertTrue(foundEmployee.isPresent());
        assertEquals("test@example.com", foundEmployee.get().getEmail());
    }

    @Test
    void testFindBySalaryGreaterThanOrderBySalaryDesc() {
        Employee employee1 = new Employee();
        employee1.setSalary(50.000);

        Employee employee2 = new Employee();
        employee2.setSalary(60.000);
        employeeRepository.saveAll(List.of(employee1, employee2));

        List<Employee> employees = employeeRepository.findBySalaryGreaterThanOrderBySalaryDesc(55000);

        assertEquals(1, employees.size());
        assertEquals(60000, employees.get(0).getSalary());
    }

    @Test
    void testFindByOrderByLastName() {
        Employee employee1 = new Employee();
        employee1.setLastName("Smith");

        Employee employee2 = new Employee();
        employee2.setLastName("Johnson");

        employeeRepository.saveAll(List.of(employee1, employee2));
        List<Employee> employees = employeeRepository.findByOrderByLastName();

        assertEquals(2, employees.size());
        assertEquals("Johnson", employees.get(0).getLastName());
        assertEquals("Smith", employees.get(1).getLastName());
    }

}
