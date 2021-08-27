package com.godrej.surveys.sitevisit.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.godrej.surveys.sitevisit.service.SiteVisitSurveyService;

//@Configuration
public class SiteVisitSurveyReminder {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SiteVisitSurveyService surveyService;
		
	 @Scheduled(cron = "0 36 10 * * *") 
	public void survey() {
		if(log.isInfoEnabled()) {
			log.info("%s %s","Survey Onboarding - ", Calendar.getInstance().getTime());
		}
		String sfid_1 = "a1l6F000002dTpoQAE";
		String sfid_2 = "a1l6F000008fqcuQAA";
		surveyService.sendSurvey(sfid_1, null, null);
		surveyService.sendSurvey(sfid_2, null, null);
		String sfid_3 = "a1l6F000008DnniQAC";
		surveyService.sendSurvey(sfid_3, null, null);

	}
	 
	 
	 @Scheduled(cron = "0 01 17 * * *") 
		public void survey2() {
			if(log.isInfoEnabled()) {
				log.info("%s %s","Survey Onboarding - ", Calendar.getInstance().getTime());
			}
			String sfid_1 = "a1l6F000002dTpoQAE";
			String sfid_2 = "a1l6F000008fqcuQAA";
			surveyService.sendSurvey(sfid_1, null, null);
			surveyService.sendSurvey(sfid_2, null, null);
			String sfid_3 = "a1l6F000008DnniQAC";
			surveyService.sendSurvey(sfid_3, null, null);

		}
	 
		@Scheduled(cron = "0 10 8 * * *") 
		public void reminder() {
			if(log.isInfoEnabled()) {
				log.info(String.format("%s %s" , "Sending reminder - ", Calendar.getInstance().getTime()));
			}
			List<String> regions =  new ArrayList<>();
			regions.add("Mumbai");
		/* surveyService.sendReminderToAllRegions(); */
			surveyService.sendReminderRegionWise(regions);
			}


}
