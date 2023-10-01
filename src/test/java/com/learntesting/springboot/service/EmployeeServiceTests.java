package com.learntesting.springboot.service;

import com.learntesting.springboot.exception.RessourceNotFoundException;
import com.learntesting.springboot.model.Employee;
import com.learntesting.springboot.repository.EmployeeRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @InjectMocks
    private EmployeeServiceImp employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    private Employee employee;
    @BeforeEach
    public void setup(){
        //  employeeRepository = Mockito.mock(EmployeeRepository.class);
        // employeeService = new EmployeeServiceImp(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com")
                .build();
    }
    //Junit test for Save Employee
    @DisplayName("Junit test for Save Employee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        //given  pre-condition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        //when action or behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then verify the input
        assertThat(savedEmployee).isNotNull();
    }
    //Junit test for Save Employee which throws exception
    @DisplayName("Junit test for Save Employee which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenReturnThrowsException(){
        //given  pre-condition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
        //when action or behaviour that we are going to test
        Assertions.assertThrows(RessourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });
        //then verify the input
        verify(employeeRepository,never()).save(any(Employee.class));
    }
    //Junit test for get all Employee
    @DisplayName("Junit test for get all Employee")
    @Test
    public void givenEmployeeList_whenGetAllEmployee_thenReturnEmployeeList(){
        //given  pre-condition or setup
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstname("Bahni")
                .lastname("Rida1")
                .email("rida1@gmail.com")
                .build();
        given(employeeRepository.findAll())
                .willReturn(List.of(employee,employee1));
        //when action or behaviour that we are going to test
        List<Employee> EmployeeList = employeeService.getAllEmployee();
        //then verify the input
        assertThat(EmployeeList).isNotNull();
        assertThat(EmployeeList.size()).isEqualTo(2);
    }
    //Junit test for get all Employee(negative scenario)
    @DisplayName("Junit test for get all Employee(negative scenario)")
    @Test
    public void givenEmployeeEmptyList_whenGetAllEmployee_thenReturnEmployeeEmptyList(){
        //given  pre-condition or setup
        given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());
        //when action or behaviour that we are going to test
        List<Employee> EmployeeList = employeeService.getAllEmployee();
        //then verify the input
        assertThat(EmployeeList).isEmpty();
        assertThat(EmployeeList.size()).isEqualTo(0);
    }
    //Junit test for get Employee by id
    @DisplayName("Junit test for get Employee by id")
    @Test
    public void givenEmployee_whenGetEmployeeById_thenReturnEmployeeObject(){
        //given  pre-condition or setup
        given(employeeRepository.findById(employee.getId()))
                .willReturn(Optional.of(employee));
        //when action or behaviour that we are going to test
        Optional<Employee> employeeById = employeeService.getEmployeeById(employee.getId());
        //then verify the input
        assertThat(employeeById).isNotNull();
    }
    //Junit test for Update Employee
    @DisplayName("Junit test for Update Employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject(){
        //given  pre-condition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setFirstname("Bahni_00");
        employee.setLastname("Rida");
        //when action or behaviour that we are going to test
        Employee updateEmployee = employeeService.updateEmployee(employee);
        //then verify the input
        assertThat(updateEmployee.getFirstname()).isEqualTo("Bahni_00");
        assertThat(updateEmployee.getLastname()).isEqualTo("Rida");
    }
    //Junit test for Delete Employee
    @DisplayName("Junit test for delete Employee")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnVoid(){
        //given  pre-condition or setup
        willDoNothing().given(employeeRepository).deleteById(employee.getId());
        //when action or behaviour that we are going to test
        employeeService.deleteEmployee(employee.getId());
        //then verify the input
        verify(employeeRepository,times(1)).deleteById(employee.getId());
    }

    @DisplayName("Junit test for find employee by firstname")
    @Test
    public void givenEmployeeObject_whenGetEmployeebyFirstname_thenReturnEmployee(){
        //given  pre-condition or setup
        given(employeeRepository.findByFirstname(employee.getFirstname())).willReturn(Optional.of(employee));
        //when action or behaviour that we are going to test
        Optional<Employee> employeeOptional = employeeService.getEmployeeByFirstname(employee.getFirstname());
        //then verify the input
        assertThat(employeeOptional).isNotNull();

    }
}
