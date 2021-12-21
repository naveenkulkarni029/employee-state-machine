package com.wm.employee.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wm.employee.domain.EmployeeDTO;
import com.wm.employee.domain.EmployeeStateInformationDTO;
import com.wm.employee.enums.EmployeeStateType;
import com.wm.employee.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

	@InjectMocks
	private EmployeeController employeeController;

	@Mock
	private EmployeeService employeeService;

	private ObjectMapper objectMapper;

	private EmployeeDTO employeeDTOResponse;

	private List<EmployeeDTO> responseEmployeeDTOList;

	@BeforeEach
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();

		File employeeDTOResponseFile = new File("src/test/resources/employee-dto-response.json");
		Assertions.assertTrue(employeeDTOResponseFile.exists());
		employeeDTOResponse = objectMapper.readValue(employeeDTOResponseFile, EmployeeDTO.class);

		responseEmployeeDTOList = new ArrayList<>();
		responseEmployeeDTOList.add(employeeDTOResponse);
	}

	@Test
	public void saveEmployeeTestSuccess() {
		Mockito.when(employeeService.createEmployee(Mockito.any())).thenReturn(employeeDTOResponse);
		EmployeeDTO actualResponse = employeeController.createEmployee(Mockito.any());
		Assertions.assertEquals(employeeDTOResponse, actualResponse);
	}

	@Test
	public void getEmployeeListTestSuccess() {
		Mockito.when(employeeService.getEmployees()).thenReturn(responseEmployeeDTOList);
		List<EmployeeDTO> actualEmployeeDTOResponse = employeeController.listEmployees();
		Assertions.assertEquals(responseEmployeeDTOList, actualEmployeeDTOResponse);
	}

	@Test
	public void getEmployeeTestSuccess() {
		Mockito.when(employeeService.getEmployee(Mockito.anyLong())).thenReturn(employeeDTOResponse);
		EmployeeDTO actualEmployeeResponse = employeeController.getEmployee(Mockito.anyLong());
		Assertions.assertEquals(employeeDTOResponse, actualEmployeeResponse);
	}

	@Test
	public void changeEmployeeStateSuccessTest() {
		EmployeeStateInformationDTO employeeStateInformationDTO = new EmployeeStateInformationDTO();
		employeeStateInformationDTO.setId(2);
		employeeStateInformationDTO.setStateType(EmployeeStateType.IN_CHECK);
		employeeDTOResponse.getStateInformations().add(employeeStateInformationDTO);
		Mockito.when(employeeService.changeEmployeeState(Mockito.anyLong(), Mockito.any()))
				.thenReturn(employeeDTOResponse);
		EmployeeDTO actualEmployeeDTOResponse = employeeController.changeEmployeeState(Mockito.anyLong(),
				Mockito.any());
		Assertions.assertEquals(employeeDTOResponse, actualEmployeeDTOResponse);
	}
}
