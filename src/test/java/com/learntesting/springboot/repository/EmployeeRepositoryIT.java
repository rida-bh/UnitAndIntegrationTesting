package com.learntesting.springboot.repository;

import com.learntesting.springboot.integration.AbstractionContainerBaseTest;
import com.learntesting.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIT extends AbstractionContainerBaseTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;
    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida@gmail.com")
                .build();
    }
    //Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida@gmail.com")
                .build();*/
        //when
        Employee savedEmployee=employeeRepository.save(employee);
        //then0
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }
    //Junit test for get all employee operation
    @DisplayName("Junit test for get all employee operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenReturnListEmployee(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when
        List<Employee> employees=employeeRepository.findAll();
        //then0
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(1);

    }
    //Junit test for get by employee id operation
    @DisplayName("Junit test for get by employee id operation")
    @Test
    public void givenEmployeesList_whenFindByID_thenReturnEmployee(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //employeeRepository.save(employee2);
        //when
        Employee employe=employeeRepository.findById(employee.getId()).get();
        //then0
        assertThat(employe).isNotNull();
    }
    @DisplayName("Junit test for get by employee email operation")
    @Test
    public void givenEmployeesList_whenFindByEmail_thenReturnEmployee(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida2@gmail.com")
                .build();*/
        employeeRepository.save(employee);
      //  employeeRepository.save(employee2);
        //when
        Employee employe=employeeRepository.findByEmail(employee.getEmail()).get();
        //then0
        assertThat(employe).isNotNull();
    }
    //Junit test for update employee  operation
    @DisplayName("Junit test for update employee operation")
    @Test
    public void givenEmployee_whenSave_thenReturnUpdatedEmployee(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("rida")
                .lastname("Bahni")
                .email("rida@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when
        Employee savedEmployee=employeeRepository.findByEmail(employee.getEmail()).get();
        savedEmployee.setEmail("rida1@gmail.com");
        savedEmployee.setFirstname("Bahni");
        savedEmployee.setLastname("Rida");
        Employee updatedEmployee =employeeRepository.save(employee);
        //then0
        assertThat(updatedEmployee.getEmail()).isEqualTo("rida1@gmail.com");
        assertThat(updatedEmployee.getFirstname()).isEqualTo("Bahni");
        assertThat(updatedEmployee.getLastname()).isEqualTo("Rida");
    }
    //Junit test for update employee  operation
    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployee_whenDelete_thenRemoveEmployee(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional= employeeRepository.findById(employee.getId());
        //then0
        assertThat(employeeOptional).isEmpty();
    }
    //Junit test for custom query using JPQL operation
    @DisplayName("Junit test for custom query using JPQL operation")
    @Test
    public void givenEmployee_whenFindByName_thenReturnEmployeeObject(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when
        Employee savedEmployee=employeeRepository.findByJPQL(employee.getFirstname(),employee.getLastname());

        //then0
        assertThat(savedEmployee).isNotNull();
    }
    //Junit test for custom query using JPQL with params operation
    @DisplayName("Junit test for custom query using JPQL with params operation")
    @Test
    public void givenEmployee_whenFindByJPQLWithParams_thenReturnEmployeeObject(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when
        Employee savedEmployee=employeeRepository.findByJPQLWithParams(employee.getFirstname(),employee.getLastname());

        //then0
        assertThat(savedEmployee).isNotNull();
    }
    //Junit test for custom query using SQL with index params operation
    @DisplayName("Junit test for custom query using SQL with index params operation")
    @Test
    public void givenEmployee_whenFindBySQLWithIndexParams_thenReturnEmployeeObject(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when
        Employee savedEmployee=employeeRepository.findByNativeSQL(employee.getFirstname(),employee.getLastname());

        //then0
        assertThat(savedEmployee).isNotNull();
    }
    @DisplayName("Junit test for custom query using SQL with params operation")
    @Test
    public void givenEmployee_whenFindBySQLWithParams_thenReturnEmployeeObject(){
        //given
/*        Employee employee = Employee.builder()
                .firstname("Bahni")
                .lastname("Rida")
                .email("rida@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when
        Employee savedEmployee=employeeRepository.findByNativeSQLithParams(employee.getFirstname(),employee.getLastname());

        //then0
        assertThat(savedEmployee).isNotNull();
    }
/*    //Junit test for ... operation
    @DisplayName("Junit test for ... operation")
    @Test
    public void given_when_then(){
        //given  pre-condition or setup

        //when action or behaviour that we are going to test

        //then verify the input

    }*/
}
