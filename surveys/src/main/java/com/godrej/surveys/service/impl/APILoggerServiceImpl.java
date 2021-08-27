package com.godrej.surveys.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godrej.surveys.dao.APILoggerDao;
import com.godrej.surveys.dto.APILoggerDto;
import com.godrej.surveys.service.APILoggerService;

@Service
public class APILoggerServiceImpl implements APILoggerService{

	@Autowired
	private APILoggerDao apiLoggerDao;
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public int insert(APILoggerDto apiLogger) {
		try {
			return apiLoggerDao.insert(apiLogger);
		}catch (Exception e) {
			log.error("Error", e);
		}
		return 0;
	}

	@Override
	public int updateResponse(APILoggerDto apiLogger) {
		try {
			return apiLoggerDao.updateResponse(apiLogger);
		}catch (Exception e) {
			log.error("Error", e);
		}
		return 0;
	}

}
