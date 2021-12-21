package com.wm.employee.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wm.employee.config.EmployeeAllowedStates;
import com.wm.employee.domain.EmployeeDTO;
import com.wm.employee.domain.EmployeeStateInformationDTO;
import com.wm.employee.enums.EmployeeStateType;
import com.wm.employee.exception.EmployeeNotFoundException;
import com.wm.employee.exception.EmployeeStateException;
import com.wm.employee.exception.EmployeeValidationException;
import com.wm.employee.mapper.EmployeeMapper;
import com.wm.employee.model.Employee;
import com.wm.employee.model.EmployeeStateInformation;
import com.wm.employee.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private EmployeeMapper employeeMapper;

	private ObjectMapper objectMapper;

	private EmployeeDTO employeeDTORequest;

	private EmployeeDTO employeeDTOResponse;

	private Employee employeeEntityRequest;

	private Employee employeeEntityResponse;

	private List<Employee> responseEmployeeEntityList;

	private List<EmployeeDTO> responseEmployeeDTOList;

	@BeforeEach
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();

		File employeeDTORequestFile = new File("src/test/resources/employee-dto-request.json");
		Assertions.assertTrue(employeeDTORequestFile.exists());
		employeeDTORequest = objectMapper.readValue(employeeDTORequestFile, EmployeeDTO.class);

		File employeeDTOResponseFile = new File("src/test/resources/employee-dto-response.json");
		Assertions.assertTrue(employeeDTOResponseFile.exists());
		employeeDTOResponse = objectMapper.readValue(employeeDTOResponseFile, EmployeeDTO.class);

		File employeeEntityRequestFile = new File("src/test/resources/employee-entity-request.json");
		Assertions.assertTrue(employeeEntityRequestFile.exists());
		employeeEntityRequest = objectMapper.readValue(employeeEntityRequestFile, Employee.class);

		File employeeEntityResponseFile = new File("src/test/resources/employee-entity-response.json");
		Assertions.assertTrue(employeeEntityResponseFile.exists());
		employeeEntityResponse = objectMapper.readValue(employeeEntityResponseFile, Employee.class);

		responseEmployeeDTOList = new ArrayList<>();
		responseEmployeeDTOList.add(employeeDTOResponse);
		responseEmployeeEntityList = new ArrayList<>();
		responseEmployeeEntityList.add(employeeEntityResponse);
	}

	@Test
	public void createEmployeeTestSuccess() {

		Mockito.when(employeeMapper.toEntity(Mockito.any())).thenReturn(employeeEntityRequest);
		Mockito.when(employeeRepository.saveAndFlush(Mockito.any())).thenReturn(employeeEntityResponse);
		Mockito.when(employeeMapper.toDTO(Mockito.any())).thenReturn(employeeDTOResponse);
		EmployeeDTO actualEmployeeDTOResponse = employeeService.createEmployee(employeeDTORequest);
		Assertions.assertEquals(employeeDTOResponse, actualEmployeeDTOResponse);
	}

	@Test
	public void createEmployeeValidationAgeLessThan18Test() {

		employeeDTORequest.setAge(0);
		Assertions.assertThrows(EmployeeValidationException.class,
				() -> employeeService.createEmployee(employeeDTORequest));
	}

	@Test
	public void createEmployeeValidationAgeMoreThan100Test() {

		employeeDTORequest.setAge(180);
		Assertions.assertThrows(EmployeeValidationException.class,
				() -> employeeService.createEmployee(employeeDTORequest));
	}

	@Test
	public void createEmployeeValidationSalaryMoreTest() {

		employeeDTORequest.setSalary(500000);
		Assertions.assertThrows(EmployeeValidationException.class,
				() -> employeeService.createEmployee(employeeDTORequest));
	}

	@Test
	public void createEmployeeValidationSalaryLessTest() {

		employeeDTORequest.setSalary(500);
		Assertions.assertThrows(EmployeeValidationException.class,
				() -> employeeService.createEmployee(employeeDTORequest));
	}

	@Test
	public void createEmployeeValidationEmailTest() {

		employeeDTORequest.setEmailId("");
		Assertions.assertThrows(EmployeeValidationException.class,
				() -> employeeService.createEmployee(employeeDTORequest));
	}

	@Test
	public void createEmployeeValidationEmailNullTest() {

		employeeDTORequest.setEmailId(null);
		Assertions.assertThrows(EmployeeValidationException.class,
				() -> employeeService.createEmployee(employeeDTORequest));
	}

	@Test
	public void createEmployeeValidationMobileNullTest() {

		employeeDTORequest.setMobileNumber(null);
		Assertions.assertThrows(EmployeeValidationException.class,
				() -> employeeService.createEmployee(employeeDTORequest));
	}

	@Test
	public void createEmployeeValidationMobileTest() {

		employeeDTORequest.setMobileNumber("");
		Assertions.assertThrows(EmployeeValidationException.class,
				() -> employeeService.createEmployee(employeeDTORequest));
	}

	@Test
	public void getEmployeeByIdTestSuccess() {
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employeeEntityResponse));
		Mockito.when(employeeMapper.toDTO(Mockito.any())).thenReturn(employeeDTOResponse);
		EmployeeDTO actualEmployeeDTOResponse = employeeService.getEmployee(Mockito.anyLong());
		Assertions.assertEquals(employeeDTOResponse, actualEmployeeDTOResponse);
	}

	@Test
	public void getEmployeeByIdExceptionTestSuccess() {
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(Mockito.anyLong()));
	}

	@Test
	public void getEmployeesTestSuccess() {
		Mockito.when(employeeRepository.findAll()).thenReturn(responseEmployeeEntityList);
		Mockito.when(employeeMapper.toDTO(Mockito.any())).thenReturn(employeeDTOResponse);
		List<EmployeeDTO> actualEmployeeDTOList = employeeService.getEmployees();
		Assertions.assertEquals(responseEmployeeDTOList, actualEmployeeDTOList);
	}

	@Test
	public void changeEmployeeStateExceptionTest() {
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Assertions.assertThrows(EmployeeNotFoundException.class,
				() -> employeeService.changeEmployeeState(1, getEmployeeStateInformationDTO()));
	}

	@Test
	public void employeeStateChangeExceptionTest() {
		ReflectionTestUtils.setField(employeeService, "employeeAllowedStates", getEmployeeAllowedState());
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employeeEntityResponse));
		EmployeeStateInformationDTO employeeStateInformationDTO = getEmployeeStateInformationDTO();
		employeeStateInformationDTO.setStateType(EmployeeStateType.APPROVED);
		Assertions.assertThrows(EmployeeStateException.class,
				() -> employeeService.changeEmployeeState(1, employeeStateInformationDTO));
	}

	@Test
	public void employeeStateActiveExceptionTest() {
		ReflectionTestUtils.setField(employeeService, "employeeAllowedStates", getEmployeeAllowedState());
		employeeEntityResponse.setCurrentState(EmployeeStateType.ACTIVE);
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employeeEntityResponse));

		EmployeeStateInformationDTO employeeStateInformationDTO = getEmployeeStateInformationDTO();
		employeeStateInformationDTO.setStateType(EmployeeStateType.ACTIVE);

		Assertions.assertThrows(EmployeeStateException.class,
				() -> employeeService.changeEmployeeState(1, employeeStateInformationDTO));
	}

	@Test
	public void employeeStateActiveToAddedExceptionTest() {
		ReflectionTestUtils.setField(employeeService, "employeeAllowedStates", getEmployeeAllowedState());
		employeeEntityResponse.setCurrentState(EmployeeStateType.ACTIVE);
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employeeEntityResponse));

		EmployeeStateInformationDTO employeeStateInformationDTO = getEmployeeStateInformationDTO();
		employeeStateInformationDTO.setStateType(EmployeeStateType.ADDED);

		Assertions.assertThrows(EmployeeStateException.class,
				() -> employeeService.changeEmployeeState(1, employeeStateInformationDTO));
	}

	@Test
	public void employeeStateTestSuccess() {
		ReflectionTestUtils.setField(employeeService, "employeeAllowedStates", getEmployeeAllowedState());
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employeeEntityResponse));

		EmployeeStateInformation employeeStateInformation = new EmployeeStateInformation();
		employeeStateInformation.setId(2);
		employeeStateInformation.setStateType(EmployeeStateType.IN_CHECK);

		employeeEntityResponse.getEmployeeStateInformationList().add(employeeStateInformation);

		Mockito.when(employeeRepository.saveAndFlush(Mockito.any())).thenReturn(employeeEntityResponse);
		EmployeeStateInformationDTO employeeStateInformationDTO = getEmployeeStateInformationDTO();
		employeeStateInformationDTO.setStateType(EmployeeStateType.IN_CHECK);

		EmployeeStateInformationDTO stateInformationDTO = new EmployeeStateInformationDTO();

		stateInformationDTO.setId(2);
		stateInformationDTO.setStateType(EmployeeStateType.IN_CHECK);

		employeeDTOResponse.getStateInformations().add(stateInformationDTO);
		Mockito.when(employeeMapper.toDTO(Mockito.any())).thenReturn(employeeDTOResponse);

		EmployeeDTO actualEmployeeStateChangeObject = employeeService.changeEmployeeState(1,
				employeeStateInformationDTO);

		Assertions.assertEquals(employeeDTOResponse, actualEmployeeStateChangeObject);

	}

	private EmployeeStateInformationDTO getEmployeeStateInformationDTO() {
		EmployeeStateInformationDTO info = new EmployeeStateInformationDTO();
		info.setStateType(EmployeeStateType.ADDED);
		return info;
	}

	private EmployeeAllowedStates getEmployeeAllowedState() {
		EmployeeAllowedStates employeeAllowedStates = new EmployeeAllowedStates();
		List<String> addedAllowedState = Arrays.asList("IN_CHECK");
		List<String> inCheckAllowedState = Arrays.asList("APPROVED");
		List<String> approvedAllowedState = Arrays.asList("IN_CHECK", "ACTIVE");

		Map<String, List<String>> allowedStatesMap = new HashMap<>();
		allowedStatesMap.put("ADDED", addedAllowedState);
		allowedStatesMap.put("IN_CHECK", inCheckAllowedState);
		allowedStatesMap.put("APPROVED", approvedAllowedState);
		employeeAllowedStates.setAllowedStates(allowedStatesMap);
		return employeeAllowedStates;

	}

}
