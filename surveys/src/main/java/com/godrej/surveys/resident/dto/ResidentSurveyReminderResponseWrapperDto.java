package com.godrej.surveys.resident.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResidentSurveyReminderResponseWrapperDto {

	private ResidentSurveyReminderResponseDto response;
	private ResidentSurveyResponseStatusDto status;
	
	public ResidentSurveyReminderResponseDto getResponse() {
		return response;
	}
	public void setResponse(ResidentSurveyReminderResponseDto response) {
		this.response = response;
	}
	public ResidentSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(ResidentSurveyResponseStatusDto status) {
		this.status = status;
	}
	
	
}
