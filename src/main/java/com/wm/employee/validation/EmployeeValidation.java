package com.wm.employee.validation;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.wm.employee.controller.advice.EmployeeControllerAdvice;
import com.wm.employee.domain.EmployeeDTO;
import com.wm.employee.exception.EmployeeValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeValidation {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static final Pattern VALID_MOBILE_NUMBER = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");

	/**
	 * The method validates the age, salary, format of the email id and the mobile
	 * number. If on of the validation is failed. The method throws an Employee
	 * Validation Exception.
	 * 
	 * @param employeeDTO
	 */
	public static void validate(EmployeeDTO employeeDTO) {
		String message = null;
		// validate if the age is between 18 to 100
		if (employeeDTO.getAge() < 18) {
			message = "Employee Age must be 18 or more.";
		} else if (employeeDTO.getAge() > 100) {
			message = "Employee Age must be below 100.";
		}
		// validate if salary is between 5000 to 200000
		else if (employeeDTO.getSalary() < 5000) {
			message = "Employee Salary must be 5000 or more.";
		} else if (employeeDTO.getSalary() > 200000) {
			message = "Minimum Employee Salary must be below 200000.";
		}
		// validate if the email id
		else if (employeeDTO.getEmailId() == null
				|| !VALID_EMAIL_ADDRESS_REGEX.matcher(employeeDTO.getEmailId()).find()) {
			message = "Invalid Email Id format";
		}

		// validate the mobile number
		else if (employeeDTO.getMobileNumber() == null
				|| !VALID_MOBILE_NUMBER.matcher(employeeDTO.getMobileNumber()).find()) {
			message = "Invalid Mobile Number format";
		}
		if (message != null) {
			log.info("validation Error message: " + message);
			throw new EmployeeValidationException(message);
		}

	}

}
