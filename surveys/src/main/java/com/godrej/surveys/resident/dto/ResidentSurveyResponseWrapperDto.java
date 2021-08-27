package com.godrej.surveys.resident.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResidentSurveyResponseWrapperDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2584173246431610247L;
	private ResidentSurveyResponseDto response;
	private ResidentSurveyResponseStatusDto status;
	
	public ResidentSurveyResponseDto getResponse() {
		return response;
	}
	public void setResponse(ResidentSurveyResponseDto response) {
		this.response = response;
	}
	public ResidentSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(ResidentSurveyResponseStatusDto status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RMSurveyResponseWrapperDto [response=" + response + ", status=" + status + "]";
	}
	
}
