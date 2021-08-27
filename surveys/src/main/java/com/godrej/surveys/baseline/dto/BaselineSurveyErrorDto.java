package com.godrej.surveys.baseline.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;
/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaselineSurveyErrorDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8686051531942867260L;
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
