package com.swamy.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.swamy.exception.ResourceNotFoundException;
import com.swamy.model.Employee;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.impl.EmployeeServiceImpl;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private IEmployeeService employeeService;

	private Employee employee;

	@BeforeEach
	public void setup() {
		employee = Employee.builder().id(1).firstName("swamy").lastName("masna").email("swamy@gmail.com").build();
	}

	@DisplayName("JUnit for Save Employee Operation")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployee() {

		// given
		given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
		given(employeeRepository.save(employee)).willReturn(employee);

		// when
		Employee savedEmployee = employeeService.saveEmployee(employee);

		// then
		assertThat(savedEmployee).isNotNull();
	}

	@DisplayName("JUnit for Save Employee Operation (Other Approach)")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployee2() {

		// given
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
		when(employeeRepository.save(employee)).thenReturn(employee);

		// when
		Employee savedEmployee = employeeService.saveEmployee(employee);

		// then
		assertThat(savedEmployee).isNotNull();
	}

	
	@DisplayName("JUnit for Save Employee Operation Which Throws Exception")
	@Test
	public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {

		// given
		given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

		// when
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.saveEmployee(employee);
		});

		// then
		verify(employeeRepository, never()).save(employee);
	}

	@DisplayName("JUnit Test for Get All Employees Operation")
	@Test
	public void givenEmployeesList_whenFindAllEmployees_thenReturnListOfEmployees() {

		// given
		given(employeeRepository.findAll()).willReturn(List.of(employee));

		// when
		List<Employee> employeesList = employeeService.getAllEmployees();

		// then
		assertThat(employeesList).isNotNull();
		assertThat(employeesList.size()).isEqualTo(1);
	}

	@DisplayName("JUnit Test for Get All Employees Operation (Negative Scenario)")
	@Test
	public void givenEmptyEmployeesList_whenFindAllEmployees_thenReturnEmptyEmployeesList() {

		// given
		given(employeeRepository.findAll()).willReturn(Collections.emptyList());

		// when
		List<Employee> employeesList = employeeService.getAllEmployees();

		// then
		assertThat(employeesList).isEmpty();
		assertThat(employeesList.size()).isEqualTo(0);
	}

	@DisplayName("JUnit Test for Get Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenFindById_thenReturnEmployee() {

		// given
		given(employeeRepository.findById(1)).willReturn(Optional.of(employee));

		// when
		Employee empObj = employeeService.getEmployeeById(employee.getId()).get();

		// then
		assertThat(empObj).isNotNull();
	}

	@DisplayName("JUnit Test for Update Employee Operation")
	@Test
	public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {

		// given
		given(employeeRepository.save(employee)).willReturn(employee);
		employee.setFirstName("Tarak");
		employee.setLastName("nm");
		employee.setEmail("tarak@gmail.com");

		// when
		Employee updatedEmployee = employeeService.saveEmployee(employee);

		// then
		assertThat(updatedEmployee).isNotNull();
		assertThat(updatedEmployee.getFirstName()).isEqualTo("Tarak");
	}

	@DisplayName("JUnit Test for Update Employee Operation (Negative Scenario)")
	@Test
	public void givenEmptyEmployee_whenUpdateEmployee_thenThrowsException() {

		// given
		given(employeeRepository.save(employee)).willReturn(null);
		employee.setFirstName("Tarak");
		employee.setLastName("nm");
		employee.setEmail("tarak@gmail.com");

		// when
		Employee updatedEmployee = employeeService.saveEmployee(employee);

		// then
		assertThat(updatedEmployee).isNull();
	}

	@DisplayName("JUnit Test for Delete Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenDeleteById_thenNothing() {

		Integer employeeId = 1;

		// given
		BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeId);

		// when
		employeeService.deleteEmployee(employeeId);

		// then
		verify(employeeRepository, times(1)).deleteById(employeeId);
	}

	@DisplayName("JUnit Test for Delete Employee By Id Operation (Negative Scenario)")
	@Test
	public void givenNullAsId_whenDeleteById_thenThrowsException() {

		Integer employeeId = null;

		// given
		BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeId);

		// when
		employeeService.deleteEmployee(employeeId);

		// then
		verify(employeeRepository, times(1)).deleteById(employeeId);
	}
}
