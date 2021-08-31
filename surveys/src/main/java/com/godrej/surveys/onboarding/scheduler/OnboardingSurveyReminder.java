package com.godrej.surveys.onboarding.scheduler;

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
import com.godrej.surveys.onboarding.service.OnboardingSurveyService;
import com.godrej.surveys.service.ProjectService;
import com.godrej.surveys.util.AppConstants;

@Configuration
public class OnboardingSurveyReminder {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private OnboardingSurveyService surveyService;
	
	@Autowired
	private ProjectService projectService;
		
	@Autowired
	private SpringSftpController springSftpController;
	
	 /*@Scheduled(cron = "0 01 12 * * *") every day 12:00 pm*/
	@Scheduled(cron = "0 0 09 * * ?")  /*every day 09:00 am  */
	/*@Scheduled(cron = "0 0/2 * * * ?") */
	public void survey() {
		if(log.isInfoEnabled()) {
			log.info("%s %s","Survey Onboarding - ", Calendar.getInstance().getTime());
		}
		String sfid_1 = "a1l6F000002dTpoQAE";
		String sfid_2 = "a1l6F000008fqcuQAA";
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formatDateTime = now.format(formatter);
        
//		String instanceId = "All-Onboard"+formatDateTime+"-"+ Calendar.getInstance().getTimeInMillis();
        String instanceId = "GPL-custonboarding-survey-All-Onboard-"+formatDateTime;
		/*surveyService.sendSurvey(sfid_1, null, null,instanceId);
		surveyService.sendSurvey(sfid_2, null, null,instanceId);
		String sfid_3 = "a1l6F000008DnniQAC";*/
		//surveyService.sendSurvey(null, null, null,instanceId);
		
		List<ProjectDto> projectList =  projectService.getProjectsForScheduler();
		List<OnboardingSurveyContactDto> contactsList = new ArrayList<OnboardingSurveyContactDto>();
		for(ProjectDto dto:projectList)
		{
			/*log.info("sfid {}",dto.getSfid());*/
			log.info("name {}",dto.getName());
			contactsList = surveyService.sendSurveyWithScheduler(dto.getSfid(), null, null,instanceId);
		}
		//contactsList = surveyService.sendSurveyWithScheduler("a1l6F000008DnniQAC", null, null,instanceId);
		log.info("Entrys Size: {}",contactsList.size());
		if(contactsList.size()>0)
		{
			OnboardingSurveyExcelHelper excelHelper = new OnboardingSurveyExcelHelper();
			try {
				excelHelper.tutorialsToExcel(contactsList,instanceId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			springSftpController.sftpcon("D:\\Satheesh\\Projects\\Litmus World\\CustOnboard\\"+instanceId+".csv",AppConstants.LW_ONBOARDING_SURVEY_FOLDER_PATH);
		}

	}
	 
	 /*@Scheduled(cron="0 0/2 * * * ?")*//* 2 min schedular time*/
		//@Scheduled(cron = "0 05 8 * * *") 
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
