package com.godrej.surveys.onboarding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OnboardingSurveyReminderResponseWrapperDto {

	private OnboardingSurveyReminderResponseDto response;
	private OnboardingSurveyResponseStatusDto status;
	
	public OnboardingSurveyReminderResponseDto getResponse() {
		return response;
	}
	public void setResponse(OnboardingSurveyReminderResponseDto response) {
		this.response = response;
	}
	public OnboardingSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(OnboardingSurveyResponseStatusDto status) {
		this.status = status;
	}
	
	
}
