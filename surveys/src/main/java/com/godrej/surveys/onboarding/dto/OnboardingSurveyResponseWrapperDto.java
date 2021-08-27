package com.godrej.surveys.onboarding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnboardingSurveyResponseWrapperDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2584173246431610247L;
	private OnboardingSurveyResponseDto response;
	private OnboardingSurveyResponseStatusDto status;
	
	public OnboardingSurveyResponseDto getResponse() {
		return response;
	}
	public void setResponse(OnboardingSurveyResponseDto response) {
		this.response = response;
	}
	public OnboardingSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(OnboardingSurveyResponseStatusDto status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RMSurveyResponseWrapperDto [response=" + response + ", status=" + status + "]";
	}
	
}
