package com.godrej.surveys.service;

import com.godrej.surveys.dto.APILoggerDto;

public interface APILoggerService {

	public int insert(APILoggerDto apiLogger);
	public int updateResponse(APILoggerDto apiLogger);

}
