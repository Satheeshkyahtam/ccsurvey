package com.godrej.surveys.customervisit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerVisitSurveyResponseWrapperDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2584173246431610247L;
	private CustomerVisitSurveyResponseDto response;
	private CustomerVisitSurveyResponseStatusDto status;
	
	public CustomerVisitSurveyResponseDto getResponse() {
		return response;
	}
	public void setResponse(CustomerVisitSurveyResponseDto response) {
		this.response = response;
	}
	public CustomerVisitSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(CustomerVisitSurveyResponseStatusDto status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RMSurveyResponseWrapperDto [response=" + response + ", status=" + status + "]";
	}
	
}
