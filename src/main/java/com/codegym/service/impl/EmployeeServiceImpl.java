package com.codegym.service.impl;

import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    Environment env;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public void save(EmployeeForm employeeForm) {
        Employee employee = saveFileEmployee(employeeForm);
        employeeRepository.save(employee);
    }

    @Override
    public void remove(Long id) {
        employeeRepository.delete(id);
    }

    @Override
    public Employee saveFileEmployee(EmployeeForm employeeForm) {
        // lay ten file
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();


        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (fileName.equals("") && employeeForm.getId() != null){
            Employee employee = findById(employeeForm.getId());
            fileName = employee.getAvatar();
        }

        // tao doi tuong de luu vao db
        if (employeeForm.getId() == null) {
            return new Employee(employeeForm.getName(), employeeForm.getBirthDate(), employeeForm.getAddress(), fileName, employeeForm.getSalary(), employeeForm.getDepartment());
        } else {
            return new Employee(employeeForm.getId(),employeeForm.getName(), employeeForm.getBirthDate(), employeeForm.getAddress(), fileName, employeeForm.getSalary(), employeeForm.getDepartment());
        }
    }
}
