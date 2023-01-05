package com.swamy.service;

import java.util.List;
import java.util.Optional;

import com.swamy.model.Employee;

public interface IEmployeeService {

	Employee saveEmployee(Employee employee);

	List<Employee> getAllEmployees();

	Optional<Employee> getEmployeeById(Integer id);

	Employee updateEmployee(Employee employee);

	void deleteEmployee(Integer id);
}
