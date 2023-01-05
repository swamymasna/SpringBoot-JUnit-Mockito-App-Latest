package com.swamy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swamy.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Optional<Employee> findByEmail(String email);

	@Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
	Employee findByJPQL(String firstName, String lastName);

	@Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
	Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

	@Query(value = "SELECT * FROM EMPLOYEE_TBL e WHERE e.FIRST_NAME=?1 AND e.LAST_NAME=?2", nativeQuery = true)
	Employee findByNativeQueryWithIndexParams(String firstName, String lastName);
	
	@Query(value = "SELECT * FROM EMPLOYEE_TBL e WHERE e.FIRST_NAME=:firstName AND e.LAST_NAME=:lastName" , nativeQuery = true)
	Employee findByNativeQueryWithNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);
	
}



