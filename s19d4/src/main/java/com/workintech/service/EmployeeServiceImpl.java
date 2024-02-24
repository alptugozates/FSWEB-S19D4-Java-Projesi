package com.workintech.service;

import com.workintech.entity.Employee;
import com.workintech.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public List<Employee> findByOrderByLastName() {
        return employeeRepository.findByOrderByLastName();
    }

    @Override
    public Employee saveOrUpdate(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findBySalaryGreaterThanOrderBySalaryDesc(double salary) {
        return employeeRepository.findBySalaryGreaterThanOrderBySalaryDesc(salary);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
