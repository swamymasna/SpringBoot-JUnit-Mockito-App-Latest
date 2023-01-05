package com.swamy.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swamy.exception.ResourceNotFoundException;
import com.swamy.model.Employee;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee saveEmployee(Employee employee) {

		Optional<Employee> employeeObj = employeeRepository.findByEmail(employee.getEmail());

		if (employeeObj.isPresent()) {
			throw new ResourceNotFoundException("Employee Already Exists With Given Email : " + employee.getEmail());
		}

		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(Integer id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(Integer id) {
		employeeRepository.deleteById(id);
	}

}
