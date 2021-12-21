package com.wm.employee.exception;

public class EmployeeNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4256242741316292524L;
	
	public EmployeeNotFoundException(String message) {
		super(message);
	}

}
