package com.wm.employee.service;

import java.util.List;

import com.wm.employee.domain.EmployeeDTO;
import com.wm.employee.domain.EmployeeStateInformationDTO;

public interface EmployeeService {

	/**
	 * This method is used to create an employee or save to the database.
	 * @param employee
	 * @return {@link EmployeeDTO}
	 */
	EmployeeDTO createEmployee(EmployeeDTO employee);

	/**
	 * This method is used to get the detail of the employee from the database.
	 * @param employeeId
	 * @return {@link EmployeeDTO}
	 */
	EmployeeDTO getEmployee(long employeeId);

	/**
	 * This method is used to get all the employees from the database.
	 * @return 
	 */
	List<EmployeeDTO> getEmployees();

	/**
	 * This method is used to update the employee state for the requested employee.
	 * @param employeeId
	 * @param employeeStateInformationDTO
	 * @return {@link EmployeeDTO}
	 */
	EmployeeDTO changeEmployeeState(long employeeId, EmployeeStateInformationDTO employeeStateInformationDTO);

}
