package com.wm.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wm.employee.domain.EmployeeDTO;
import com.wm.employee.domain.EmployeeStateInformationDTO;
import com.wm.employee.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * Employee State Transition Controller is an entry point for the application.
 * This Controller supports four end points.
 * 
 * <p>
 * 1. Create an Employee: The endpoint is a HTTP Post request which accepts the
 * EmployeeDTO as a request body. Returns {@code HttpStatus} 201 Created on
 * successfully saving the Employee into the database. Along with the Response
 * code 201. The endpoint also returns the store employee details for further
 * process. On failure the endpoint will throw an duplicate email id exception.
 * 
 * <p>
 * 2. Get Employee by Id: The endpoint is a HTTP Get Request which accepts the
 * employeeId as the Path variable. Returns a {@code HttpStatus} 200 OK on
 * successfully fetch of employee from the database. Returns {@code HttpStatus}
 * 404 NOT FOUND if the requested employee Id is not found in the database.
 * 
 * <p>
 * 3. Get All Employees: The endpoint is a HTTP Get Request which Returns a
 * {@code HttpStatus} 200 OK irrespective of data being available or not in the
 * database.
 * 
 * <p>
 * 4. Update the Employee State Transition: The endpoint is a HTTP PUT Request
 * accepts the employee Id as a Path Variable and the EmployeeStateType as a
 * request body. The accepted list of EmployeeStateType are found here
 * {@code EmployeeStateType}. On successfully updating the employee state the
 * endpoint returns a {@code HttpStatus} 200 OK along with the Employee details
 * and its current state. On failure returns an Employee State Exception error
 * message along with the HTTP Status Code 400.
 *
 * @author Naveen Kulkarni
 */

@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * Create an Employee: The endpoint is a HTTP Post request which accepts the
	 * EmployeeDTO as a request body. Returns {@code HttpStatus} 201 Created on
	 * successfully saving the Employee into the database. Along with the Response
	 * code 201. The endpoint also returns the store employee details for further
	 * process. On failure the endpoint will throw an duplicate email id exception.
	 * 
	 * @param employeeDTO
	 * @return {@code EmployeeDTO}
	 */
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.createEmployee(employeeDTO);
	}

	/**
	 * Get Employee by Id: The endpoint is a HTTP Get Request which accepts the
	 * employeeId as the Path variable. Returns a {@code HttpStatus} 200 OK on
	 * successfully fetch of employee from the database. Returns {@code HttpStatus}
	 * 404 NOT FOUND if the requested employee Id is not found in the database.
	 * 
	 * @param employeeId
	 * @return {@code EmployeeDTO}
	 */
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "/{employeeId}")
	public EmployeeDTO getEmployee(@PathVariable long employeeId) {
		return employeeService.getEmployee(employeeId);
	}

	/**
	 * The endpoint is a HTTP Get Request which Returns a {@code HttpStatus} 200 OK
	 * irrespective of data being available or not in the database.
	 * 
	 * @return {@code List} of {@code EmployeeDTO}
	 */
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "")
	public List<EmployeeDTO> listEmployees() {
		return employeeService.getEmployees();
	}

	/**
	 * Update the Employee State Transition: The endpoint is a HTTP PUT Request
	 * accepts the employee Id as a Path Variable and the EmployeeStateType as a
	 * request body. The accepted list of EmployeeStateType are found here
	 * {@code EmployeeStateType}. On successfully updating the employee state the
	 * endpoint returns a {@code HttpStatus} 200 OK along with the Employee details
	 * and its current state.
	 * 
	 * @param employeeId
	 * @param employeeStateInformationDTO
	 * @return {@code EmployeeDTO}
	 */
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping(value = "/{employeeId}")
	public EmployeeDTO changeEmployeeState(@PathVariable long employeeId,
			@RequestBody EmployeeStateInformationDTO employeeStateInformationDTO) {
		return employeeService.changeEmployeeState(employeeId, employeeStateInformationDTO);
	}

}
