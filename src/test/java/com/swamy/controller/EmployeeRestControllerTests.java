package com.swamy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swamy.model.Employee;
import com.swamy.service.IEmployeeService;

@WebMvcTest
public class EmployeeRestControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IEmployeeService employeeService;

	private Employee employee;

	private String empJson;

	@BeforeEach
	public void setup() throws Exception {

		employee = Employee.builder().id(1).firstName("swamy").lastName("masna").email("swamy@gmail.com").build();

		ObjectMapper objectMapper = new ObjectMapper();
		empJson = objectMapper.writeValueAsString(employee);
	}

	@DisplayName("JUnit Test for Save Employee Operation (Positive Scenario)")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() throws Exception {

		// given
		// BDDMockito.given(employeeService.saveEmployee(employee)).willReturn(employee);
		when(employeeService.saveEmployee(employee)).thenReturn(employee);

		// when
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees/")
				.contentType(MediaType.APPLICATION_JSON).content(empJson);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// then
		assertThat(status).isEqualTo(201);
	}

	@DisplayName("JUnit Test for Save Employee Operation (Negative Scenario)")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee2() throws Exception {

		// given
		when(employeeService.saveEmployee(employee)).thenReturn(null);

		// when
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees/")
				.contentType(MediaType.APPLICATION_JSON).content("");

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// then
		assertEquals(status, 400);
//		assertThat(status).isEqualTo(400);
	}

	@DisplayName("JUnit Test for Get All Employees Operation")
	@Test
	public void givenEmployeesList_whenFindAllEmployees_thenReturnListOfEmployees() throws Exception {

		// given
		when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

		// when
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/");

		int status = mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();

		// then
		assertEquals(status, 200);

	}

	@DisplayName("JUnit Test for Get One Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenFindEmployee_thenReturnEmployee() throws Exception {

		// given setup
		when(employeeService.getEmployeeById(employee.getId())).thenReturn(Optional.of(employee));

		// when action
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/" + employee.getId());

		int status = mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();

		// then verify the result
		assertEquals(status, 200);
	}

	@DisplayName("JUnit Test for Update Employee Operation")
	@Test
	public void givenEmployeeId_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {

		// given
		employee.setEmail("abc@gmail.com");
		when(employeeService.updateEmployee(employee)).thenReturn(employee);

		// when

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/employees/")
				.contentType(MediaType.APPLICATION_JSON).content(empJson);

		int status = mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();

		// then
		assertEquals(status, 200);
	}

	@DisplayName("JUnit Test for Delete Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenDeletedEmployee() throws Exception {

		// given
		when(employeeService.getEmployeeById(employee.getId())).thenReturn(Optional.of(employee));
		employeeService.deleteEmployee(employee.getId());

		// when
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/employees/" + employee.getId()).contentType(MediaType.APPLICATION_JSON).content(empJson);

		int status = mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();

		// then
		assertEquals(status, 200);
	}
}
