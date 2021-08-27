package com.godrej.surveys.resident.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResidentSurveyReminderResponseDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5747959972243813446L;
	private String status;
	private String error;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
