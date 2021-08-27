package com.godrej.surveys.customervisit.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.godrej.surveys.customervisit.service.CustomerVisitSurveyService;

//@Configuration
public class CustomerVisitSurveyReminder {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerVisitSurveyService surveyService;
		
	 @Scheduled(cron = "0 19 10 * * *") 
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
	 /* Scheduler test*/
	/*
	 * @Scheduled(cron = "0 11 5 * * *") public void test() {
	 * if(log.isInfoEnabled()) { log.info("%s %s","Survey Onboarding - ",
	 * Calendar.getInstance().getTime()); } System.out.println("Logging.."); try {
	 * Thread.sleep(60000); System.out.println("Woke."); }catch (Exception e) { //
	 * TODO: handle exception }
	 * 
	 * }
	 * 
	 * @Scheduled(cron = "0 11 5 * * *") public void test1() {
	 * if(log.isInfoEnabled()) { log.info("%s %s","Survey Onboarding - ",
	 * Calendar.getInstance().getTime()); } System.out.println("Logging 1..");
	 * 
	 * }
	 */

		@Scheduled(cron = "0 01 8 * * *") 
		public void firstReminder() {
			if(log.isInfoEnabled()) {
				log.info(String.format("%s %s" , "Sending reminder - ", Calendar.getInstance().getTime()));
			}
			
			List<String> regions =  new ArrayList<>();
			regions.add("Mumbai");
		/* surveyService.sendReminderToAllRegions(); */
			surveyService.sendReminderRegionWise(regions);
		}
}
