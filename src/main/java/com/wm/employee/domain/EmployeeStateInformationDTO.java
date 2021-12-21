package com.wm.employee.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.wm.employee.enums.EmployeeStateType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;

@Data
public class EmployeeStateInformationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 237749822042115525L;

	@Schema(accessMode = AccessMode.READ_ONLY)
	@JsonProperty("employeeStateId")
	private long id;

	@Schema(name = "employeeStateType")
	@JsonProperty("employeeStateType")
	private EmployeeStateType stateType;

	@Schema(accessMode = AccessMode.READ_ONLY)
	@JsonIgnore
	private String createdBy ="SELF";

	@Schema(accessMode = AccessMode.READ_ONLY)
	@JsonIgnore
	private LocalDateTime createdDateTime = LocalDateTime.now();
}
