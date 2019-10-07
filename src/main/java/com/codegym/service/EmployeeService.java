package com.codegym.service;

import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;

public interface EmployeeService {
    Iterable<Employee> findAll();
    Employee findById(Long id);
    void save(EmployeeForm employeeForm);
    void remove(Long id);
    Employee saveFileEmployee(EmployeeForm employeeForm);

}
