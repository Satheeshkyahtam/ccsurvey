package com.godrej.surveys.dto;

import com.godrej.surveys.dto.BaseDto;

public class APILoggerDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1361341251884839676L;

	private Integer apiLoggerId;
	private String request;
	private String response;
	
	public APILoggerDto() {
		/* Empty Contructor*/
	}
	
	public APILoggerDto(String request, String response) {
		this.request= request;
		this.response= response;
	}
	
	public Integer getApiLoggerId() {
		return apiLoggerId;
	}
	public void setApiLoggerId(Integer apiLoggerId) {
		this.apiLoggerId = apiLoggerId;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
