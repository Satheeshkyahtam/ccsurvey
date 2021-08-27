package com.godrej.surveys.dto;

import java.util.HashMap;
import java.util.Map;

public class CommonResponseDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5618310624396515697L;
	
	public CommonResponseDto(String key, Object value) {
		addData(key, value);
	}
	
	public CommonResponseDto(ResponseDto response) {
		addData("response", response);
	}
	
	Map<String, Object> data =  new HashMap<>();
	
	public Map<String, Object> getData() {
		return data;
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void addData(String key, Object value) {
		data.put(key, value);
	}
	
	
}
