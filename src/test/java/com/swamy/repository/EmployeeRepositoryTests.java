package com.swamy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.swamy.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	public void setup() {
		employee = Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();

	}

	@DisplayName("JUnit Test for Save Employee Operation")
	@Test
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

		// given pre-condition or setup
		// Employee employee =
		// Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();

		// when action or behaviour
		Employee savedEmployee = employeeRepository.save(employee);

		// then verify the result
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);

	}

	@DisplayName("JUnit Test for Get All Employees Operation")
	@Test
	public void givenEmployeesList_whenFindAll_returnEmployeesList() {

		// given pre-condition or setup
		// Employee employee1 =
		// Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();

		Employee employee2 = Employee.builder().firstName("tarak").lastName("nm").email("tarak@gmail.com").build();
		employeeRepository.save(employee);
		employeeRepository.save(employee2);

		// when action or behaviour
		List<Employee> employeeList = employeeRepository.findAll();

		// then vertify the result
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
	}

	@DisplayName("JUnit Test for Get One Employee Operation")
	@Test
	public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

		// Given pre-condition or setup
		// Employee employee =
		// Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();

		employeeRepository.save(employee);

		// when action or behaviour
		Employee employeeObj = employeeRepository.findById(employee.getId()).get();

		// then verify the result
		assertThat(employeeObj).isNotNull();

	}

	@DisplayName("JUnit Test for get Employee By Email Operation")
	@Test
	public void givenEmailId_whenFindByEmail_ThenReturnEmployeeObject() {

		// given pre-condition or setup
		// Employee employee =
		// Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();

		employeeRepository.save(employee);

		// when action or behaviour
		Employee employeeObj = employeeRepository.findByEmail(employee.getEmail()).get();

		// then verify the result
		assertThat(employeeObj).isNotNull();
		assertThat(employeeObj.getEmail()).isEqualTo(employee.getEmail());
	}

	@DisplayName("JUnit Test for Update Employee By Id Operation")
	@Test
	public void givenEmployee_WhenUpdate_ThenReturnUpdatedEmployee() {

		// given pre-condition or setup
		// Employee employee =
		// Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();
		employeeRepository.save(employee);

		// when action or behaviour
		Employee emp = employeeRepository.findById(employee.getId()).get();
		emp.setFirstName("king");

		Employee updatedEmployee = employeeRepository.save(employee);

		// then vertify the result
		assertThat(updatedEmployee).isNotNull();
		assertThat(updatedEmployee.getFirstName()).isEqualTo("king");

	}

	@DisplayName("JUnit Test to Delete Employee By Id Operation")
	@Test
	public void givenEmployee_whenDeleteById_thenRemoveEmployee() {

		// given pre-condition or setup
		// Employee employee =
		// Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();
		employeeRepository.save(employee);

		// when action or behaviour
		Employee emp1 = employeeRepository.findById(employee.getId()).get();
		employeeRepository.delete(emp1);

		Optional<Employee> employee2 = employeeRepository.findByEmail("swamy@gmail.com");

		// then verify the result
		assertThat(employee2).isEmpty();
	}

	@DisplayName("JUnit Test for Custom Query using JPQL with Index")
	@Test
	public void givenFirstNameAndLastName_whenFindByJPQLIndexParams_thenReturnEmployeeObject() {

		// given setup [firstname and lastname]
		// Employee employee =
		// Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();
		employeeRepository.save(employee);

		// when action [findByJPQL]
		Employee empObj = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());

		// then verify result
		assertThat(empObj).isNotNull();
		assertThat(empObj.getId()).isGreaterThan(0);
	}

	@DisplayName("JUnit Test for Custom Query using JPQL Named Params")
	@Test
	public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {

		// given setup
		// Employee employee =
		// Employee.builder().firstName("swamy").lastName("masna").email("swamy@gmail.com").build();
		employeeRepository.save(employee);

		// when action [findByJPQL]
		Employee empObj = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());

		// then verify result
		assertThat(empObj).isNotNull();

	}

	@DisplayName("Unit Test for Custom Native Query with Index Params")
	@Test
	public void givenFirstNameAndLastName_whenFindByNativeIndexParams_thenReturnEmployeeObject() {

		// given setup
		employeeRepository.save(employee);

		// when action
		Employee empObj = employeeRepository.findByNativeQueryWithIndexParams(employee.getFirstName(),
				employee.getLastName());

		// then verify the Result
		assertThat(empObj).isNotNull();

	}

	@Test
	@DisplayName("Unit Test for Custom Native Query Using Named Params")
	public void givenFirstNameAndLastName_whenFindByNativeNamedParams_thenReturnEmployeeObject() {

		// given setup
		employeeRepository.save(employee);

		// when action
		Employee empObj = employeeRepository.findByNativeQueryWithNamedParams(employee.getFirstName(),
				employee.getLastName());

		// then verify the Result
		assertThat(empObj).isNotNull();
	}
}
