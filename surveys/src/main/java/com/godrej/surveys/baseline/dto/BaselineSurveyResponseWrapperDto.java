package com.godrej.surveys.baseline.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaselineSurveyResponseWrapperDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2584173246431610247L;
	private BaselineSurveyResponseDto response;
	private BaselineSurveyResponseStatusDto status;
	
	public BaselineSurveyResponseDto getResponse() {
		return response;
	}
	public void setResponse(BaselineSurveyResponseDto response) {
		this.response = response;
	}
	public BaselineSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(BaselineSurveyResponseStatusDto status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RMSurveyResponseWrapperDto [response=" + response + ", status=" + status + "]";
	}
	
}
