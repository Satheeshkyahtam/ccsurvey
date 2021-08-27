package com.godrej.surveys.baseline.scheduler;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.godrej.surveys.baseline.service.BaselineSurveyService;

/*@Configuration
@EnableScheduling*/
public class BaselineSurveyReminder {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaselineSurveyService baselineSurveyService;
		
	 @Scheduled(cron = "0 34 10 * * *") 
	public void firstReminder() {
		if(log.isInfoEnabled()) {
			log.info("%s %s","Reminders - ", Calendar.getInstance().getTime());
		}
		baselineSurveyService.sendReminderToAllRegions();	
	}

}
