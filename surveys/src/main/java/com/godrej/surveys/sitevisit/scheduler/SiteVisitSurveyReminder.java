package com.godrej.surveys.sitevisit.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.onboarding.controller.SpringSftpController;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyContactDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyExcelHelper;
import com.godrej.surveys.service.ProjectService;
import com.godrej.surveys.sitevisit.dto.SiteVisitSurveyContactDto;
import com.godrej.surveys.sitevisit.dto.SiteVisitSurveyExcelHelper;
import com.godrej.surveys.sitevisit.service.SiteVisitSurveyService;
import com.godrej.surveys.util.AppConstants;

@Configuration
public class SiteVisitSurveyReminder {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SiteVisitSurveyService surveyService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SpringSftpController springSftpController;
		
	@Scheduled(cron = "0 0 08 * * ?")
	public void surveyMorning() {
		if(log.isInfoEnabled()) {
			log.info("%s %s","SurveyMorning Survey Onboarding - ", Calendar.getInstance().getTime());
		}
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formatDateTime = now.format(formatter);
        String instanceId = "GPL-SiteVisit-survey-All-Onboard-"+formatDateTime;
        
        String dateWhereCondition=" and Handover_Survey_Start_Date__c::date < now()::date and Handover_Survey_Start_Date__c is not null";
        //Changes required
		List<ProjectDto> projectList =  projectService.getProjectsForScheduler(dateWhereCondition);
		List<SiteVisitSurveyContactDto> contactsList = new ArrayList<SiteVisitSurveyContactDto>();
		for(ProjectDto dto:projectList)
		{
			log.info("name {}",dto.getName());
			contactsList = surveyService.sendSurveyWithScheduler(dto.getSfid(), null, null,instanceId);
		}
		log.info("Entrys Size: {}",contactsList.size());
		if(contactsList.size()>0)
		{
			SiteVisitSurveyExcelHelper excelHelper = new SiteVisitSurveyExcelHelper();
			try {
				excelHelper.tutorialsToExcel(contactsList,instanceId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			springSftpController.sftpcon("D:\\Satheesh\\Projects\\Litmus World\\SiteVisit\\"+instanceId+".csv",AppConstants.LW_SITEVIST_SURVEY_FOLDER_PATH);
		}

	}
	
	@Scheduled(cron = "0 0 17 * * ?")
	public void surveyEvening() {
		if(log.isInfoEnabled()) {
			log.info("%s %s","SurveyEvening Survey Onboarding - ", Calendar.getInstance().getTime());
		}
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formatDateTime = now.format(formatter);
        String instanceId = "GPL-SiteVisit-survey-All-Onboard-"+formatDateTime;
        
        String dateWhereCondition=" and Site_Visit_Survey_Start_Date__c::date < now()::date and Site_Visit_Survey_Start_Date__c is not null";
        //Changes required
		List<ProjectDto> projectList =  projectService.getProjectsForScheduler(dateWhereCondition);
		List<SiteVisitSurveyContactDto> contactsList = new ArrayList<SiteVisitSurveyContactDto>();
		for(ProjectDto dto:projectList)
		{
			log.info("name {}",dto.getName());
			contactsList = surveyService.sendSurveyWithScheduler(dto.getSfid(), null, null,instanceId);
		}
		log.info("Entrys Size: {}",contactsList.size());
		if(contactsList.size()>0)
		{
			SiteVisitSurveyExcelHelper excelHelper = new SiteVisitSurveyExcelHelper();
			try {
				excelHelper.tutorialsToExcel(contactsList,instanceId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			springSftpController.sftpcon("D:\\Satheesh\\Projects\\Litmus World\\SiteVisit\\"+instanceId+".csv",AppConstants.LW_SITEVIST_SURVEY_FOLDER_PATH);
		}

	}
	 
	/* @Scheduled(cron = "0 01 17 * * *") 
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
		 surveyService.sendReminderToAllRegions(); 
			surveyService.sendReminderRegionWise(regions);
			}*/


}
