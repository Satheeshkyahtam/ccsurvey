package com.godrej.surveys.rm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RMSurveyReminderResponseWrapperDto {

	private RMSurveyReminderResponseDto response;
	private RMSurveyResponseStatusDto status;
	
	public RMSurveyReminderResponseDto getResponse() {
		return response;
	}
	public void setResponse(RMSurveyReminderResponseDto response) {
		this.response = response;
	}
	public RMSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(RMSurveyResponseStatusDto status) {
		this.status = status;
	}
	
	
}
