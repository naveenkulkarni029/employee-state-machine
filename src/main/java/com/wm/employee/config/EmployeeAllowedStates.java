package com.wm.employee.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
@ConfigurationProperties(prefix = "employee-state-machine")
public class EmployeeAllowedStates {

	private Map<String, List<String>> allowedStates;
	
	
}
