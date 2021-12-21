package com.wm.employee.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.employee.enums.ContractType;
import com.wm.employee.enums.EmployeeStateType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;

@Data
public class EmployeeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -878536164754517728L;

	@Schema(accessMode = AccessMode.READ_ONLY, name = "employeeId")
	@JsonProperty("employeeId")
	private long id;

	@Schema(name = "employeeName", example = "Naveen")
	@JsonProperty("employeeName")
	private String name;

	@Schema(name = "employeeAge", example = "18")
	@JsonProperty("employeeAge")
	private int age;
	
	@Schema(name = "employeeSalary", example = "5000")
	@JsonProperty("employeeSalary")
	private long salary;
	
	@Schema(name="employeeEmailId", example = "abc@gmail.com")
	@JsonProperty("employeeEmailId")
	private String emailId;
	
	@Schema(name="employeeMobileNumber", example = "+91 9738080788")
	@JsonProperty("employeeMobileNumber")
	private String mobileNumber;
	
	@Schema(name="employeeContractType", example = "FULL_TIME")
	@JsonProperty("employeeContractType")
	private ContractType contractType;
	
	@Schema(name = "employeeStateInformations", accessMode = AccessMode.READ_ONLY)
	@JsonProperty("employeeStateInformations")
	private List<EmployeeStateInformationDTO> StateInformations;

	@Schema(name = "employeeCurrentState", accessMode = AccessMode.READ_ONLY)
	@JsonProperty("employeeCurrentState")
	private EmployeeStateType currentState;
	
	@Schema(accessMode = AccessMode.READ_ONLY)
	@JsonIgnore
	private String createdBy = "SELF";

	@Schema(accessMode = AccessMode.READ_ONLY)
	@JsonIgnore
	private String modifiedBy;

	@Schema(accessMode = AccessMode.READ_ONLY)
	@JsonIgnore
	private LocalDateTime createdDateTime = LocalDateTime.now();

	@Schema(accessMode = AccessMode.READ_ONLY)
	@JsonIgnore
	private LocalDateTime modifiedDateTime;
}
