package com.godrej.surveys.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	private Logger log =  LoggerFactory.getLogger(getClass());
	
	public String getCurrentDateTime() {
		   LocalDateTime now = LocalDateTime.now();  
		   return AppConstants.DEFAULT_DATE_FORMATTER.format(now);  
	}
	
	public String getCurrentDate(String dateFormat) {
		   LocalDateTime now = LocalDateTime.now();  
		   DateTimeFormatter formatter=null;
		   try {
			   formatter = DateTimeFormatter.ofPattern(dateFormat);
			   return formatter.format(now);
		   }catch (Exception e) {
			   log.error("Error", e);
			   return null;
		} 
	}
}
