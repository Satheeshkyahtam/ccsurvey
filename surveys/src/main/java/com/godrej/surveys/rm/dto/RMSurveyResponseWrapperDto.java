package com.godrej.surveys.rm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RMSurveyResponseWrapperDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2584173246431610247L;
	private RMSurveyResponseDto response;
	private RMSurveyResponseStatusDto status;
	
	public RMSurveyResponseDto getResponse() {
		return response;
	}
	public void setResponse(RMSurveyResponseDto response) {
		this.response = response;
	}
	public RMSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(RMSurveyResponseStatusDto status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RMSurveyResponseWrapperDto [response=" + response + ", status=" + status + "]";
	}
	
}
