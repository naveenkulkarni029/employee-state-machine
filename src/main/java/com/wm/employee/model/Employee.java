package com.wm.employee.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.wm.employee.enums.ContractType;
import com.wm.employee.enums.EmployeeStateType;

import lombok.Data;

@Entity
@Table(name = "employees", uniqueConstraints = @UniqueConstraint(columnNames = { "emp_email_id" }))
@Data
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3077197414273678879L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_emp_id")
	private long id;

	@Column(name = "emp_name")
	private String name;

	@Column(name = "emp_age")
	private int age;

	@Column(name = "emp_salary")
	private long salary;

	@Column(name = "emp_email_id")
	private String emailId;

	@Column(name = "emp_mobile_number")
	private String mobileNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "emp_contract_type")
	private ContractType contractType;

	@OneToMany(targetEntity = EmployeeStateInformation.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_emp_id")
	private List<EmployeeStateInformation> employeeStateInformationList;

	@Column(name = "current_emp_state_type")
	@Enumerated(EnumType.STRING)
	private EmployeeStateType currentState;

	@Column(name = "creator")
	private String createdBy = "SELF";

	@Column(name = "modifier")
	private String modifiedBy;

	@Column(name = "created_date_time")
	private LocalDateTime createdDateTime = LocalDateTime.now();

	@Column(name = "modified_date_time")
	private LocalDateTime modifiedDateTime;
}
