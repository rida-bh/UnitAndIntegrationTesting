package com.learntesting.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learntesting.springboot.model.Employee;
import com.learntesting.springboot.service.EmployeeService;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;

import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private Employee employee;
    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .id(1L)
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
    }
    //Junit test for create Employee
    @DisplayName("Junit test for create Employee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
        //given  pre-condition or setup
        given(employeeService.saveEmployee(any(Employee.class))).willAnswer(
                (invocation -> invocation.getArgument(0)));
        //when action or behaviour that we are going to test
        ResultActions resultActions=mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname",
                        is(employee.getFirstname())))
                .andExpect(jsonPath("$.lastname",
                        is(employee.getLastname())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
    }
    //Junit test for get all Employees
    @DisplayName("Junit test for get all Employees")
    @Test
    public void givenListEmployees_whenGetAllEmployee_thenReturnEmployeesList() throws Exception{
        //given  pre-condition or setup
        given(employeeService.getAllEmployee()).willReturn(List.of(employee));
        //when action or behaviour that we are going to test
        ResultActions resultActions=mockMvc.perform(get("/api/employees"));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(1)));
    }
    //Junit test for get Employee by id
    // positive scenario
    @DisplayName("Junit test for get Employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeesObject() throws Exception{
        //given  pre-condition or setup
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
        //when action or behaviour that we are going to test
        ResultActions resultActions=mockMvc.perform(get("/api/employees/{id}",employee.getId()));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",
                        is(employee.getFirstname())))
                .andExpect(jsonPath("$.lastname",
                        is(employee.getLastname())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
    }
    //Junit test for get Employee by id
    // negative scenario
    @DisplayName("N :Junit test for get Employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmptyObject() throws Exception{
        //given  pre-condition or setup
        given(employeeService.getEmployeeById(2l)).willReturn(Optional.empty());
        //when action or behaviour that we are going to test
        ResultActions resultActions=mockMvc.perform(get("/api/employees/{id}",employee.getId()));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    //Junit test for update Employee
    //positive scenario
    @DisplayName("Junit test for update Employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception{
        //given  pre-condition or setup
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com").build();
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer(
                (invocation -> invocation.getArgument(0)));
        //when action or behaviour that we are going to test
        ResultActions resultActions=mockMvc.perform(put("/api/employees/{id}",employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",
                        is(updatedEmployee.getFirstname())))
                .andExpect(jsonPath("$.lastname",
                        is(updatedEmployee.getLastname())))
                .andExpect(jsonPath("$.email",
                        is(updatedEmployee.getEmail())));
    }
    //Junit test for update Employee
    //negative scenario
    @DisplayName("N:Junit test for update Employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmptyObject() throws Exception{
        //given  pre-condition or setup
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com").build();
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer(
                (invocation -> invocation.getArgument(0)));
        //when action or behaviour that we are going to test
        ResultActions resultActions=mockMvc.perform(put("/api/employees/{id}",employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    //Junit test for delete Employee
    @DisplayName("Junit test for delete Employee ")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnString() throws Exception{
        //given  pre-condition or setup
        willDoNothing().given(employeeService).deleteEmployee(employee.getId());
        //when action or behaviour that we are going to test
        ResultActions resultActions=mockMvc.perform(delete("/api/employees/{id}",employee.getId()));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isOk());
    }
       //Junit test for find by First name operation // positive
    @DisplayName("Junit test for find by First name operation")
    @Test
    public void givenEmployeeObject_whenFindEmployeeByFirstname_thenReturnEmployeeObject() throws Exception {
        //given  pre-condition or setup

        given(employeeService.getEmployeeByFirstname(employee.getFirstname())).willReturn(Optional.of(employee));

        //when action or behaviour that we are going to test
        ResultActions resultActions=mockMvc.perform(get("/api/employees/get-firstname/{firstname}",employee.getFirstname()));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",is(employee.getFirstname())))
                .andExpect(jsonPath("$.lastname",is(employee.getLastname())))
                .andExpect(jsonPath("$.email",is(employee.getEmail())));
    }
    //Junit test for find by First name operation // negative
    @DisplayName("Junit test for find by First name operation")
    @Test
    public void givenEmployeeObject_whenFindEmployeeByFirstname_thenReturnVoid() throws Exception {
        //given  pre-condition or setup
        given(employeeService.getEmployeeByFirstname(employee.getFirstname())).willReturn(Optional.empty());
        //when action or behaviour that we are going to test
        ResultActions resultActions = mockMvc.perform(get("/api/employees/get-firstname/{firstname}",employee.getFirstname()));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
