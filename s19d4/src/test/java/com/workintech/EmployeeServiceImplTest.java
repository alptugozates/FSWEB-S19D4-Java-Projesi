package com.workintech;

import com.workintech.entity.Employee;
import com.workintech.exceptions.EmployeeNotFoundException;
import com.workintech.exceptions.EmployeeServiceException;
import com.workintech.repository.EmployeeRepository;
import com.workintech.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

@SpringBootTest
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testFindAll() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindByIdEmployeeFound() {
        Long employeeId = 1L;

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(new Employee()));

        Employee result = employeeService.findById(employeeId);
        assertNotNull(result);
    }

    @Test
    void testFindByIdEmployeeNotFound() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findById(employeeId));
    }

    @Test
    void testFindByEmailEmployeeFound() {
        String email = "test@example.com";
        Mockito.when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(new Employee()));
        Optional<Employee> result = employeeService.findByEmail(email);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindByEmailEmployeeNotFound() {
        String email = "test@example.com";
        Mockito.when(employeeRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findByEmail(email));
    }

    @Test
    void testFindByOrderByLastNameEmployeesFound() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());

        Mockito.when(employeeRepository.findByOrderByLastName()).thenReturn(employees);
        List<Employee> result = employeeService.findByOrderByLastName();
        assertEquals(1, result.size());
    }

    @Test
    void testFindByOrderByLastNameEmployeesNotFound() {
        Mockito.when(employeeRepository.findByOrderByLastName()).thenReturn(new ArrayList<>());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findByOrderByLastName());
    }

    @Test
    void testSaveOrUpdate() {
        Employee employee = new Employee();

        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.saveOrUpdate(employee);
        assertNotNull(result);
    }

    @Test
    void testSaveOrUpdateError() {
        Employee employee = new Employee();
        Mockito.when(employeeRepository.save(any(Employee.class))).thenThrow(new RuntimeException());
        assertThrows(EmployeeServiceException.class, () -> employeeService.saveOrUpdate(employee));
    }

    @Test
    void testFindBySalaryGreaterThanOrderBySalaryDescEmployeesFound() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        Mockito.when(employeeRepository.findBySalaryGreaterThanOrderBySalaryDesc(anyDouble())).thenReturn(employees);
        List<Employee> result = employeeService.findBySalaryGreaterThanOrderBySalaryDesc(50000);
        assertEquals(1, result.size());
    }

    @Test
    void testFindBySalaryGreaterThanOrderBySalaryDescEmployeesNotFound() {
        Mockito.when(employeeRepository.findBySalaryGreaterThanOrderBySalaryDesc(anyDouble())).thenReturn(new ArrayList<>());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findBySalaryGreaterThanOrderBySalaryDesc(50000));
    }

    @Test
    void testDeleteById() {
        Long employeeId = 1L;
        assertDoesNotThrow(() -> employeeService.deleteById(employeeId));
    }

    @Test
    void testDeleteByIdError() {
        Long employeeId = 1L;
        Mockito.doThrow(new RuntimeException()).when(employeeRepository).deleteById(employeeId);
        assertThrows(EmployeeServiceException.class, () -> employeeService.deleteById(employeeId));
    }
    }

