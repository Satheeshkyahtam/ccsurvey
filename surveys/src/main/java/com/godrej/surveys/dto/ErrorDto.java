package com.godrej.surveys.dto;

public class ErrorDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4835094022954793439L;
	private String errorCode;
	private String errorMessage;

	public ErrorDto() {
		/* Emplty Contructor*/
	}
	
	public ErrorDto(String errorCode, String message) {
		this.errorCode =errorCode;
		this.errorMessage = message;	
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
