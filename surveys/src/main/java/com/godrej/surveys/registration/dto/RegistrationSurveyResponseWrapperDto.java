package com.godrej.surveys.registration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationSurveyResponseWrapperDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2584173246431610247L;
	private RegistrationSurveyResponseDto response;
	private RegistrationSurveyResponseStatusDto status;
	
	public RegistrationSurveyResponseDto getResponse() {
		return response;
	}
	public void setResponse(RegistrationSurveyResponseDto response) {
		this.response = response;
	}
	public RegistrationSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(RegistrationSurveyResponseStatusDto status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RMSurveyResponseWrapperDto [response=" + response + ", status=" + status + "]";
	}
	
}
