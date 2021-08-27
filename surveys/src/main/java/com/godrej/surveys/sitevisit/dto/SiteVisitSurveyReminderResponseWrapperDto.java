package com.godrej.surveys.sitevisit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteVisitSurveyReminderResponseWrapperDto {

	private SiteVisitSurveyReminderResponseDto response;
	private SiteVisitSurveyResponseStatusDto status;
	
	public SiteVisitSurveyReminderResponseDto getResponse() {
		return response;
	}
	public void setResponse(SiteVisitSurveyReminderResponseDto response) {
		this.response = response;
	}
	public SiteVisitSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(SiteVisitSurveyResponseStatusDto status) {
		this.status = status;
	}
	
	
}
