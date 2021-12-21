package com.wm.employee.controller.advice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wm.employee.enums.ContractType;
import com.wm.employee.enums.EmployeeStateType;
import com.wm.employee.exception.EmployeeNotFoundException;
import com.wm.employee.exception.EmployeeStateException;
import com.wm.employee.exception.EmployeeValidationException;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author Naveen Kulkarni
 *
 */
@RestControllerAdvice
@Slf4j
public class EmployeeControllerAdvice {

	/**
	 * This method is an exception flow for get employee by id and update the state
	 * of he employee.
	 * 
	 * @param exception
	 * @return payload
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { EmployeeNotFoundException.class })
	public Object handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
		log.info(exception.getLocalizedMessage(), exception);
		return createMessage(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * This method is an exception flow called when we try to save the employee
	 * details to the database and the email id of the employee already exists in
	 * the database.
	 * 
	 * @param exception
	 * @return payload
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public Object handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
		log.info("DataIntegrityViolationException", exception);
		return createMessage("Email id already exists", HttpStatus.BAD_REQUEST);
	}

	/**
	 * THis method is called for an exception flow if the request payload does not
	 * have the available enums {@code EmployeeStateType} or {@code ContractType} in
	 * the payloads either for the posting a request or to update an employee
	 * 
	 * @param exception
	 * @return payload
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		log.info("HttpMessageNotReadableException", exception);
		Object payload = null;
		if (exception.getLocalizedMessage().contains("com.wm.employee.enums.EmployeeStateType")) {
			payload = createMessage("Unaccepted Employee State Type. Accepted values are: "
					+ Arrays.asList(EmployeeStateType.values()) + " and are case sensitive.", HttpStatus.BAD_REQUEST);
		}
		if (exception.getLocalizedMessage().contains("com.wm.employee.enums.ContractType")) {
			payload = createMessage("Unaccepted Contract Type. Accepted values are: "
					+ Arrays.asList(ContractType.values()) + " and are case sensitive.", HttpStatus.BAD_REQUEST);
		}
		return payload;
	}

	/**
	 * This method is called for an exception flow for the POST call. The exception
	 * validationException is thrown if payload has any of the invalid data related
	 * to age, salary, email and mobile number format.
	 * 
	 * @param exception
	 * @return payload
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { EmployeeValidationException.class })
	public Object handleEmployeeValidationException(EmployeeValidationException exception) {
		log.info("EmployeeValidationException", exception);
		return createMessage(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method is called for a PUT call to update the employee state.
	 * 
	 * @param exception
	 * @return payload
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { EmployeeStateException.class })
	public Object handleEmployeeStateException(EmployeeStateException exception) {
		log.info("EmployeeStateException", exception);
		return createMessage(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param exceptionMessage
	 * @param httpStatus
	 * @return {@code Map} of payload
	 */
	private Map<String, Object> createMessage(String exceptionMessage, HttpStatus httpStatus) {
		Map<String, Object> map = new HashMap<>();
		map.put("message", exceptionMessage);
		map.put("status", httpStatus.value());
		map.put("error", httpStatus.getReasonPhrase());
		map.put("timestamp", System.currentTimeMillis());
		return map;
	}
}
