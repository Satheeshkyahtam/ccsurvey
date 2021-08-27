package com.godrej.surveys.baseline.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaselineSurveyReminderResponseWrapperDto {

	private BaselineSurveyReminderResponseDto response;
	private BaselineSurveyResponseStatusDto status;
	
	public BaselineSurveyReminderResponseDto getResponse() {
		return response;
	}
	public void setResponse(BaselineSurveyReminderResponseDto response) {
		this.response = response;
	}
	public BaselineSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(BaselineSurveyResponseStatusDto status) {
		this.status = status;
	}
	
	
}
