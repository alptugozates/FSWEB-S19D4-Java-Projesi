package com.workintech.controller;

import com.workintech.entity.Employee;
import com.workintech.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workintech/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<List<Employee>> getEmployeeByEmail(@PathVariable String email) {
        List<Employee> employees = employeeService.findByEmail(email);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/byOrder")
    public ResponseEntity<List<Employee>> getEmployeesByOrder() {
        List<Employee> employees = employeeService.findByOrderByLastName();
        return ResponseEntity.ok(employees);
    }

    // [POST] /workintech/employees
    @PostMapping
    public ResponseEntity<Employee> saveOrUpdateEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveOrUpdate(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @PostMapping("/bySalary/{salary}")
    public ResponseEntity<List<Employee>> getEmployeesBySalary(@PathVariable double salary) {
        List<Employee> employees = employeeService.findBySalaryGreaterThanOrderBySalaryDesc(salary);
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok("Employee with id " + id + " has been deleted.");
    }
}
