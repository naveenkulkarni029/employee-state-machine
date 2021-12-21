package com.wm.employee.mapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wm.employee.domain.EmployeeDTO;
import com.wm.employee.model.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeMapperTest {

	@InjectMocks
	private EmployeeMapper employeeMapper;

	private ObjectMapper objectMapper;

	private EmployeeDTO employeeDTOResponse;

	private Employee employeeResponse;

	private EmployeeDTO employeeDTORequest;

	private Employee employeeRequest;

	@BeforeEach
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();

		File employeeDTOResponseFile = new File("src/test/resources/employee-dto-response.json");
		Assertions.assertTrue(employeeDTOResponseFile.exists());
		employeeDTOResponse = objectMapper.readValue(employeeDTOResponseFile, EmployeeDTO.class);

		File employeeDTORequestFile = new File("src/test/resources/employee-dto-request.json");
		Assertions.assertTrue(employeeDTORequestFile.exists());
		employeeDTORequest = objectMapper.readValue(employeeDTORequestFile, EmployeeDTO.class);

		File employeeResponseFile = new File("src/test/resources/employee-entity-response.json");
		Assertions.assertTrue(employeeResponseFile.exists());
		employeeResponse = objectMapper.readValue(employeeResponseFile, Employee.class);

		File employeeRequestFile = new File("src/test/resources/employee-entity-request.json");
		Assertions.assertTrue(employeeRequestFile.exists());
		employeeRequest = objectMapper.readValue(employeeRequestFile, Employee.class);
	}

	@Test
	public void convertToEntityTest() {

		LocalDateTime creationDateTime = LocalDateTime.now();

		Employee actualEmployeeResponse = employeeMapper.toEntity(employeeDTORequest);
		actualEmployeeResponse.setCreatedDateTime(creationDateTime);
		employeeResponse.setCreatedDateTime(creationDateTime);
		employeeResponse.getEmployeeStateInformationList().get(0).setCreatedDateTime(creationDateTime);

		actualEmployeeResponse.setId(1);
		actualEmployeeResponse.getEmployeeStateInformationList().get(0).setId(1);
		actualEmployeeResponse.getEmployeeStateInformationList().get(0).setCreatedDateTime(creationDateTime);

		Assertions.assertEquals(employeeResponse, actualEmployeeResponse);

	}

	@Test
	public void convertToDTOTest() {

		LocalDateTime creationDateTime = LocalDateTime.now();

		EmployeeDTO actualEmployeeDTOResponse = employeeMapper.toDTO(employeeResponse);
		actualEmployeeDTOResponse.setCreatedDateTime(creationDateTime);
		actualEmployeeDTOResponse.getStateInformations().get(0).setCreatedDateTime(creationDateTime);

		actualEmployeeDTOResponse.setId(1);
		employeeDTOResponse.setId(1);
		employeeDTOResponse.setCreatedDateTime(creationDateTime);
		employeeDTOResponse.getStateInformations().get(0).setCreatedDateTime(creationDateTime);

		Assertions.assertEquals(employeeDTOResponse, actualEmployeeDTOResponse);

	}

}
