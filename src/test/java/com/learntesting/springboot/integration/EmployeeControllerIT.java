package com.learntesting.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learntesting.springboot.model.Employee;
import com.learntesting.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIT extends AbstractionContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();

    }
    //Junit test for create Employee
    @DisplayName("Junit test for create Employee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
        //given  pre-condition or setup
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
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
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
        employeeRepository.save(employee);
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
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
        employeeRepository.save(employee);
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
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
       // employeeRepository.save(employee);
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
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
        employeeRepository.save(employee);
        Employee updatedEmployee = Employee.builder()
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com").build();

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
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
        Employee updatedEmployee = Employee.builder()
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com").build();

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
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
        employeeRepository.save(employee);
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
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
        employeeRepository.save(employee);
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
        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("bahni")
                .email("rida@gmail.com").build();
        //when action or behaviour that we are going to test
        ResultActions resultActions = mockMvc.perform(get("/api/employees/get-firstname/{firstname}",employee.getFirstname()));
        //then verify the input
        resultActions
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
