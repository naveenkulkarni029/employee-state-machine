package com.wm.employee.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.wm.employee.domain.EmployeeDTO;
import com.wm.employee.domain.EmployeeStateInformationDTO;
import com.wm.employee.enums.EmployeeStateType;
import com.wm.employee.model.Employee;
import com.wm.employee.model.EmployeeStateInformation;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Naveen Kulkarni
 *
 */
@Component
@Slf4j
public class EmployeeMapper {

	/**
	 * As the method name says it is used to convert the DTO object to the entity
	 * object before saving to the database.
	 * 
	 * The method uses {@link BeanUtils} class to copy the information from the
	 * {@link EmployeeDTO} to {@link Employee}
	 * 
	 * The method also sets the {@link EmployeeStateType} as ADD while saving the
	 * employee to the database.
	 * 
	 * @param employeeDTO
	 * @return {@link Employee}
	 */
	public Employee toEntity(EmployeeDTO employeeDTO) {
		log.info("Entring toEntity ");
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);

		employee.setCurrentState(EmployeeStateType.ADDED);

		List<EmployeeStateInformation> employeeStateInformations = new ArrayList<>();

		EmployeeStateInformation employeeStateInformation = new EmployeeStateInformation();

		employeeStateInformation.setStateType(EmployeeStateType.ADDED);
		employeeStateInformations.add(employeeStateInformation);

		employee.setEmployeeStateInformationList(employeeStateInformations);

		log.info("Exiting toEntity with Employee: " + employee.toString());
		return employee;
	}

	/**
	 * As the method name says it is used to convert the Entity Object to DTO
	 * Object. The method uses the {@link BeanUtils} Class to copy the
	 * {@link Employee} to {@link EmployeeDTO} and {@link EmployeeStateInformation}
	 * to {@link EmployeeStateInformationDTO}.
	 * 
	 * @param employee
	 * @return {@link EmployeeDTO}
	 */
	public EmployeeDTO toDTO(Employee employee) {
		log.info("Entering toDTO");
		EmployeeDTO empDto = new EmployeeDTO();
		System.out.println(employee);
		BeanUtils.copyProperties(employee, empDto);

		List<EmployeeStateInformationDTO> employeeStateInformationDTOs = new ArrayList<EmployeeStateInformationDTO>();

		for (EmployeeStateInformation employeeStateInformation : employee.getEmployeeStateInformationList()) {
			EmployeeStateInformationDTO employeeStateInformationDTO = new EmployeeStateInformationDTO();
			BeanUtils.copyProperties(employeeStateInformation, employeeStateInformationDTO);
			employeeStateInformationDTOs.add(employeeStateInformationDTO);
		}
		empDto.setStateInformations(employeeStateInformationDTOs);
		log.info("Exiting toDTO with EmpDTO: " + empDto);
		return empDto;

	}

}
