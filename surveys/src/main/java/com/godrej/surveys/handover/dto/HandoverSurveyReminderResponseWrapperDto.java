package com.godrej.surveys.handover.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HandoverSurveyReminderResponseWrapperDto {

	private HandoverSurveyReminderResponseDto response;
	private HandoverSurveyResponseStatusDto status;
	
	public HandoverSurveyReminderResponseDto getResponse() {
		return response;
	}
	public void setResponse(HandoverSurveyReminderResponseDto response) {
		this.response = response;
	}
	public HandoverSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(HandoverSurveyResponseStatusDto status) {
		this.status = status;
	}
	
	
}
