package com.codegym.repository;

import com.codegym.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
}
