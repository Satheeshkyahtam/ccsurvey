package com.godrej.surveys.sitevisit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteVisitSurveyResponseWrapperDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2584173246431610247L;
	private SiteVisitSurveyResponseDto response;
	private SiteVisitSurveyResponseStatusDto status;
	
	public SiteVisitSurveyResponseDto getResponse() {
		return response;
	}
	public void setResponse(SiteVisitSurveyResponseDto response) {
		this.response = response;
	}
	public SiteVisitSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(SiteVisitSurveyResponseStatusDto status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RMSurveyResponseWrapperDto [response=" + response + ", status=" + status + "]";
	}
	
}
