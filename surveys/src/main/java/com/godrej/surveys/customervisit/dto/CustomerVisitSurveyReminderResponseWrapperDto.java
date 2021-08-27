package com.godrej.surveys.customervisit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerVisitSurveyReminderResponseWrapperDto {

	private CustomerVisitSurveyReminderResponseDto response;
	private CustomerVisitSurveyResponseStatusDto status;
	
	public CustomerVisitSurveyReminderResponseDto getResponse() {
		return response;
	}
	public void setResponse(CustomerVisitSurveyReminderResponseDto response) {
		this.response = response;
	}
	public CustomerVisitSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(CustomerVisitSurveyResponseStatusDto status) {
		this.status = status;
	}
	
	
}
