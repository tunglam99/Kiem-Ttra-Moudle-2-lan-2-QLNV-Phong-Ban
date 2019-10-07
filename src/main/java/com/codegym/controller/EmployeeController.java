package com.codegym.controller;


import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("departments")
    public Iterable<Department> departments(){
        return departmentService.findAll();
    }

    @GetMapping("/employees")
    public ModelAndView listEmployees(){
        Iterable<Employee> employees = employeeService.findAll();
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees",employees);
        return modelAndView;
    }

    @GetMapping("/create-employee")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employeeForm",new EmployeeForm());
        return modelAndView;
    }

    @PostMapping("/save-employee")
    public ModelAndView saveEmployee(@ModelAttribute EmployeeForm employeeForm, BindingResult result){
        if (result.hasFieldErrors()){
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        employeeService.save(employeeForm);

        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employeeForm",new EmployeeForm());
        modelAndView.addObject("message","New Employee created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        if (employee != null){
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("employee",employee);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error:404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-employee")
    public String updateEmployee(@ModelAttribute("employee") EmployeeForm employeeForm, BindingResult result){
        if (result.hasFieldErrors()){
            System.out.println("Result Error Occured" + result.getAllErrors());
        }
        employeeService.save(employeeForm);

        return "redirect:/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        if (employee != null){
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee", employee);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error:404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-employee")
    public String deleteEmployee(@ModelAttribute("employee") EmployeeForm employeeForm){
        employeeService.remove(employeeForm.getId());
        return "redirect:/employees";
    }

}
