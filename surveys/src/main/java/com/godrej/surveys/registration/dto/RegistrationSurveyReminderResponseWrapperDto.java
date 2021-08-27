package com.godrej.surveys.registration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationSurveyReminderResponseWrapperDto {

	private RegistrationSurveyReminderResponseDto response;
	private RegistrationSurveyResponseStatusDto status;
	
	public RegistrationSurveyReminderResponseDto getResponse() {
		return response;
	}
	public void setResponse(RegistrationSurveyReminderResponseDto response) {
		this.response = response;
	}
	public RegistrationSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(RegistrationSurveyResponseStatusDto status) {
		this.status = status;
	}
	
	
}
