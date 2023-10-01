package com.learntesting.springboot.service;

import com.learntesting.springboot.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Employee updateEmployee(Employee updateEmployee);
    Optional<Employee> getEmployeeById(Long employeeId);
    List<Employee> getAllEmployee();
    void deleteEmployee(Long employeeId);
    Optional<Employee> getEmployeeByFirstname(String firstname);

}
