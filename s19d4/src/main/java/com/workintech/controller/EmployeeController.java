package com.workintech.controller;

import com.workintech.entity.Employee;
import com.workintech.exceptions.EmployeeNotFoundException;
import com.workintech.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workintech/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @GetMapping("/byEmail/{email}")
    public Employee getEmployeeByEmail(@PathVariable String email) {
        Optional<Employee> employee = employeeService.findByEmail(email);
        return employee
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with given email: " + email));
    }

    @GetMapping("/byOrder/")
    public List<Employee> getEmployeesOrderByLastName() {
        return employeeService.findByOrderByLastName();
    }

    @PostMapping
    public Employee saveOrUpdateEmployee(@RequestBody Employee employee) {
        return employeeService.saveOrUpdate(employee);
    }

    @PostMapping("/bySalary/{salary}")
    public List<Employee> getEmployeesBySalaryGreaterThan(@PathVariable double salary) {
        return employeeService.findBySalaryGreaterThanOrderBySalaryDesc(salary);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

}
