package br.spring.persistence;

import br.spring.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Created by allan on 30/11/16.
 */
public interface EmployeeDao extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT name FROM employee LIMIT 10", nativeQuery = true)
    List<String> getEmployeeNames();

    @Query(value = "SELECT emp FROM Employee emp")
    Set<Employee> findAllEmployerrss();

    @Query("select emp from Employee emp where emp.name = ?1")
    Employee findUserByName(String name);

    @Query("select emp from Employee emp where emp.name like %?1")
    List<Employee> findByNameEndsWith(String name);
}
