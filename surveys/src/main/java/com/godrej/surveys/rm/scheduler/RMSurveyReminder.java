package com.godrej.surveys.rm.scheduler;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.godrej.surveys.rm.service.RMSurveyService;

//@Configuration
public class RMSurveyReminder {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RMSurveyService rmSurveyService;
		
	@Scheduled(cron = "0 44 10 * * *") 
	public void firstReminder() {
		if(log.isInfoEnabled()) {
			log.info(String.format("%s %s" , "Sending reminder - ", Calendar.getInstance().getTime()));
		}
		rmSurveyService.sendReminderToAllRegions();	
	}

}
