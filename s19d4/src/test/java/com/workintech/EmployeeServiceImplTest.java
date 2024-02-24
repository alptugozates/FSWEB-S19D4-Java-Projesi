package com.workintech;

import com.workintech.entity.Employee;
import com.workintech.repository.EmployeeRepository;
import com.workintech.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void testFindByEmail() {

        Mockito.when(employeeRepository.findByEmail("test@example.com")).thenReturn(Arrays.asList(new Employee()));
        List<Employee> employees = employeeService.findByEmail("test@example.com");
        assertEquals(1, employees.size());
    }

    @Test
    public void testFindBySalaryGreaterThanOrderBySalaryDesc() {
        Mockito.when(employeeRepository.findBySalaryGreaterThanOrderBySalaryDesc(50000.0)).thenReturn(Arrays.asList(new Employee()));
        List<Employee> employees = employeeService.findBySalaryGreaterThanOrderBySalaryDesc(50000.0);
        assertEquals(1, employees.size());
    }
    @Test
    public void testFindByOrderByLastName() {
        Mockito.when(employeeRepository.findByOrderByLastName()).thenReturn(Arrays.asList(new Employee()));
        List<Employee> employees = employeeService.findByOrderByLastName();
        assertEquals(1, employees.size());
    }
}
