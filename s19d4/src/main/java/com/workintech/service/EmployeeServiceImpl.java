package com.workintech.service;

import com.workintech.entity.Employee;
import com.workintech.exceptions.EmployeeNotFoundException;
import com.workintech.exceptions.EmployeeServiceException;
import com.workintech.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with given id: " + id));
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if(optionalEmployee.isPresent()){
            return optionalEmployee;
        } else {
            throw new EmployeeNotFoundException("Employee not found with given email: " + email);
        }
    }

    @Override
    public List<Employee> findByOrderByLastName() {
       List<Employee> employees = employeeRepository.findByOrderByLastName();
       if(employees.isEmpty()){
           throw new EmployeeNotFoundException("Employees not found with given last name");
       }
       return employees;
    }

    @Override
    public Employee saveOrUpdate(Employee employee) {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new EmployeeServiceException("Error while saving or updating employee", e);
        }
    }

    @Override
    public List<Employee> findBySalaryGreaterThanOrderBySalaryDesc(double salary) {
        List<Employee> optionalEmployee = employeeRepository.findBySalaryGreaterThanOrderBySalaryDesc(salary);
        if(optionalEmployee.isEmpty()){
            throw new EmployeeNotFoundException("Employees not found with the given salary: " + salary);
        } else {
            return optionalEmployee;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            throw new EmployeeServiceException("Error while deleting employee by id" + id, e);
        }
    }
}
