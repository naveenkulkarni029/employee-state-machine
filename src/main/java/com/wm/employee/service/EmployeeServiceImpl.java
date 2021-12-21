package com.wm.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wm.employee.config.EmployeeAllowedStates;
import com.wm.employee.domain.EmployeeDTO;
import com.wm.employee.domain.EmployeeStateInformationDTO;
import com.wm.employee.enums.EmployeeStateType;
import com.wm.employee.exception.EmployeeNotFoundException;
import com.wm.employee.exception.EmployeeStateException;
import com.wm.employee.mapper.EmployeeMapper;
import com.wm.employee.model.Employee;
import com.wm.employee.model.EmployeeStateInformation;
import com.wm.employee.repository.EmployeeRepository;
import com.wm.employee.validation.EmployeeValidation;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Naveen Kulkarni
 *
 */
@Service
@Transactional
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeAllowedStates employeeAllowedStates;

	private EmployeeRepository employeeRepository;

	private EmployeeMapper employeeMapper;

	/**
	 * Parameterized constructor
	 * 
	 * @param employeeRepository
	 * @param employeeMapper
	 * @param employeeAllowedStates
	 */
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
			EmployeeAllowedStates employeeAllowedStates) {
		this.employeeRepository = employeeRepository;
		this.employeeMapper = employeeMapper;
		this.employeeAllowedStates = employeeAllowedStates;
	}

	@Override
	public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
		log.info("Entering createEmployee with employee: " + employeeDTO);
		EmployeeValidation.validate(employeeDTO);
		Employee employee = employeeMapper.toEntity(employeeDTO);
		employee = employeeRepository.saveAndFlush(employee);
		return employeeMapper.toDTO(employee);
	}

	@Override
	public EmployeeDTO getEmployee(long employeeId) {
		log.info("Entering getEmployee with employeeId: " + employeeId);
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee Not found: " + employeeId));
		return employeeMapper.toDTO(employee);
	}

	@Override
	public List<EmployeeDTO> getEmployees() {
		log.info("Entering getEmployees ");
		List<Employee> employees = employeeRepository.findAll();
		List<EmployeeDTO> employeesDto = new ArrayList<>();
		for (Employee employee : employees) {
			EmployeeDTO employeeDTO = employeeMapper.toDTO(employee);
			employeesDto.add(employeeDTO);
		}
		log.info("Exiting getEmployees: " + employeesDto);
		return employeesDto;
	}

	@Override
	public EmployeeDTO changeEmployeeState(long employeeId, EmployeeStateInformationDTO employeeStateInformationDTO) {
		log.info("Entering changeEmployeeState with employeeId: " + employeeId + " and EmployeeStateInformationDTO: "
				+ employeeStateInformationDTO);

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee Not found: " + employeeId));
		log.info("Employee data with employeeId: " + employeeId + " is " + employee);
		if (validateEmployeeState(employee.getCurrentState().toString(),
				employeeStateInformationDTO.getStateType().toString())) {
			employee.setCurrentState(employeeStateInformationDTO.getStateType());
			employee.setModifiedBy(employeeStateInformationDTO.getCreatedBy());
			employee.setModifiedDateTime(employeeStateInformationDTO.getCreatedDateTime());

			EmployeeStateInformation employeeStateInformation = new EmployeeStateInformation(
					employeeStateInformationDTO.getStateType());

			employee.getEmployeeStateInformationList().add(employeeStateInformation);

			employee = employeeRepository.saveAndFlush(employee);
		}
		return employeeMapper.toDTO(employee);

	}

	/**
	 * The method will validate the employee current state with the requested state
	 * and provides the below information for different scenarios
	 * {@link EmployeeAllowedStates} is a class which as a map of String and list of
	 * accepted states for each step.
	 * 
	 * <p>
	 * If current state is ADDED. The next transition state will be IN_CHECK.
	 * Likewise from the IN_CHECK state it can transit to APPROVED, APPROVED state
	 * to either IN_CHECK or ACTIVE state. Once it reaches the active state. There
	 * is no further states available for the application.
	 * 
	 * 1. If the requested state and the current state are same then the method will
	 * throw an {@link EmployeeStateException} along with the message 'current state
	 * is same as the requested state.'
	 * 
	 * 2. If the requested state is not matching the allowed state from the current
	 * state flow like mentioned in above paragraph. Then it will throw the
	 * {@link EmployeeStateException} along with the message 'Employee State
	 * Transition is not Allowed with the Requested Employee State Type.'
	 * 
	 * 3. If the current state is active then there is no further flow for the
	 * application. Hence it throw an {@link EmployeeStateException} along with the
	 * message 'Their no further state transition allowed for the requested
	 * Employee'
	 * 
	 * @param currentEmployeeState
	 * @param requestedEmployeeState
	 */
	private boolean validateEmployeeState(String currentEmployeeState, String requestedEmployeeState) {

		if (currentEmployeeState.equals(requestedEmployeeState)) {
			throw new EmployeeStateException("Requested Employee State is the same as the current Employee State");
		}

		if (EmployeeStateType.ACTIVE.toString().equals(currentEmployeeState)) {
			throw new EmployeeStateException("Their no further state transition allowed for the requested Employee");
		}

		List<String> allowedEmployeeStates = employeeAllowedStates.getAllowedStates()
				.get(currentEmployeeState.toString());

		if (!allowedEmployeeStates.contains(requestedEmployeeState)) {
			throw new EmployeeStateException(
					"Employee State Transition is not Allowed with the Requested Employee State Type. Allowed State is or are: "
							+ allowedEmployeeStates);
		}
		return true;
	}

}
