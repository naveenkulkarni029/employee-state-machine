package com.wm.employee.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wm.employee.enums.EmployeeStateType;

import lombok.Data;

@Entity
@Table(name = "employee_states")
@Data
public class EmployeeStateInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3769694883235840137L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "state_id")
	private long id;

	@Column(name = "emp_state_type")
	@Enumerated(EnumType.STRING)
	private EmployeeStateType stateType;

	@Column(name = "creator")
	private String createdBy = "SELF";

	@Column(name = "created_date_time")
	private LocalDateTime createdDateTime = LocalDateTime.now();
	
	public EmployeeStateInformation() {
		
	}
	
	public EmployeeStateInformation(EmployeeStateType stateType) {
		this.stateType = stateType;
	}
}