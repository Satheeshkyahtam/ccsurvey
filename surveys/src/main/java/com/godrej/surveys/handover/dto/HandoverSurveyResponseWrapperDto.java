package com.godrej.surveys.handover.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HandoverSurveyResponseWrapperDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2584173246431610247L;
	private HandoverSurveyResponseDto response;
	private HandoverSurveyResponseStatusDto status;
	
	public HandoverSurveyResponseDto getResponse() {
		return response;
	}
	public void setResponse(HandoverSurveyResponseDto response) {
		this.response = response;
	}
	public HandoverSurveyResponseStatusDto getStatus() {
		return status;
	}
	public void setStatus(HandoverSurveyResponseStatusDto status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RMSurveyResponseWrapperDto [response=" + response + ", status=" + status + "]";
	}
	
}
