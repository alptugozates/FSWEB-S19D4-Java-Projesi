package com.workintech.service;

import com.workintech.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(Long id);

    List<Employee> findByEmail(String email);

    List<Employee> findByOrderByLastName();

    Employee saveOrUpdate(Employee employee);

    List<Employee> findBySalaryGreaterThanOrderBySalaryDesc(double salary);

    void deleteById(Long id);
}
