package com.QuestionnaireProject.QuestionnaireSystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseRtnInfoProp {
	
	@JsonProperty("status_code")
	private String statusCode;
	
	private String message;
	
	public BaseRtnInfoProp() {
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
