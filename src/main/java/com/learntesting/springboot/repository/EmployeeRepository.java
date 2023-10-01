package com.learntesting.springboot.repository;

import com.learntesting.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByFirstname(String firstname);
    @Query("SELECT e from Employee e where e.firstname =?1 and e.lastname= ?2")
    Employee findByJPQL(String firstname,String lastname);
    @Query("SELECT e from Employee e where e.firstname =:firstName and e.lastname= :lastName")
    Employee findByJPQLWithParams(@Param("firstName") String firstName,@Param("lastName") String lastName);

    @Query(value = "select * from employee e where e.first_name =?1 and e.last_name=?2",nativeQuery = true)
    Employee findByNativeSQL(String firstName,String lastName);

    @Query(value = "select * from employee e where e.first_name =:firstName and e.last_name=:lastName",nativeQuery = true)
    Employee findByNativeSQLithParams(@Param("firstName") String firstName,@Param("lastName") String lastName);
}
