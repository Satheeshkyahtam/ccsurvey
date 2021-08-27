package com.godrej.surveys.dto;

import java.util.ArrayList;
import java.util.List;

public class Errors {

	private List<ErrorDto> errorList = new ArrayList<>();
	
	public List<ErrorDto> getErrorList() {
		return errorList;
	}

	public void addError(String errorCode, String message){	
		ErrorDto error =  new ErrorDto(errorCode, message);
		errorList.add(error);
	}
	
	public int getErrorCount(){
		return errorList.size();
	}
	
}
