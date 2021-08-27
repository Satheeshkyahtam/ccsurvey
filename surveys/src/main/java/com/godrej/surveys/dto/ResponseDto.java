package com.godrej.surveys.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3988613149114219664L;
	private boolean hasError;
	private String message;
	List<ErrorDto> errors = new ArrayList<>();
	Map<String, Object> data =  new HashMap<>();
	
	public ResponseDto() {
		/*
		 * Empty Constructor 
		 */
	}
	public ResponseDto(boolean hasError, String message) {
		this.hasError =  hasError;
		this.message =  message;
	}
	
	public ResponseDto(boolean hasError, String message, ErrorDto error) {
		this.hasError =  hasError;
		this.message =  message;
		errors.add(error);
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ErrorDto> getErrors() {
		return errors;
	}
	public void setErrors(List<ErrorDto> errors) {
		this.errors = errors;
	}
	public Map<String, Object> getData() {
		return data;
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void addData(String key, Object value) {
		data.put(key, value);
	}
	
	public Object getData(String key) {
		return data.get(key);
	}

}
