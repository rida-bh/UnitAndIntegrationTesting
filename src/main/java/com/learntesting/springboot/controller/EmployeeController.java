package com.learntesting.springboot.controller;

import com.learntesting.springboot.model.Employee;
import com.learntesting.springboot.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }
    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployee();
    }
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId){
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable("id") Long employeeId,@RequestBody Employee employee){
        return employeeService.getEmployeeById(employeeId).map(
                savedEmployee->{
                    savedEmployee.setFirstname(employee.getFirstname());
                    savedEmployee.setLastname(employee.getLastname());
                    savedEmployee.setEmail(employee.getEmail());
                   Employee updatedEmployee= employeeService.updateEmployee(savedEmployee);
                   return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
                }).orElseGet(()->ResponseEntity.notFound().build());
    }
    @DeleteMapping ("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
         employeeService.deleteEmployee(employeeId);
            return new ResponseEntity<String>("Employee deleted successfully!",HttpStatus.OK);
    }
    @GetMapping("/get-firstname/{firstname}")
    public ResponseEntity<Employee> getEmployeeByFirstname(@PathVariable("firstname") String firstname){
        return employeeService.getEmployeeByFirstname(firstname)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
