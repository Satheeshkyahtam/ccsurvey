package com.godrej.surveys.baseline.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.godrej.surveys.baseline.dto.BaselineSurveyContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyExcelHelper;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderContactDto;
import com.godrej.surveys.baseline.service.BaselineSurveyAPIService;
import com.godrej.surveys.baseline.service.BaselineSurveyService;
import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.onboarding.controller.SpringSftpController;
import com.godrej.surveys.service.ProjectService;
import com.godrej.surveys.util.AppConstants;

@Controller
public class BaseLineSurveyAdminContoller {


	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private BaselineSurveyAPIService rmSurveyApi;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BaselineSurveyService rmSurveyService;
	
	@Autowired
	private SpringSftpController springSftpController;
	
	@GetMapping(value = "/pr/surveyAdminBL")
	public ModelAndView openSurveyAdminPage() {
		ModelAndView view = new ModelAndView();
		try {
			List<String> regions = projectService.getRegions();
			view.addObject("regions", regions);
		}catch (Exception e) {
			log.error("Error", e);
		}
		view.setViewName("pro/survey/bl/surveyAdmin");
		return view;
	}

	@PostMapping("/pr/getContactsBL")
	public @ResponseBody ResponseDto getContacts(@RequestParam("projectSfid") String projectSfid, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
			//@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		//List<BaselineSurveyContactDto> contacts =  rmSurveyService.getContacts(projectSfid, fromDate, toDate);
		List<BaselineSurveyContactDto> contacts =  rmSurveyService.getContacts(projectSfid, fromDate, toDate);
		ResponseDto response = new ResponseDto(false, "");
		response.addData("contacts", contacts);
		response.addData("count", Integer.valueOf(contacts.size()));
		return response;
	}
	
	@PostMapping("/pr/sendSurveyBL")
	public @ResponseBody ResponseDto sendSurvey(@RequestParam("projectSfid") String projectSfid
			,@RequestParam ("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		ResponseDto response = null;
		try {
			LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
	        String formatDateTime = now.format(formatter);
	        
	        String instanceId = "Baseline_"+formatDateTime;
	        
			response = rmSurveyService.sendSurvey(projectSfid,fromDate,toDate, instanceId);
		}catch (Exception e) {
			log.error("Error", e);
			response =  new ResponseDto(true, "Problem while sending Survey");
		}
		return response;
	}
	@GetMapping(value = "testBaselineSurvey")
	public String testSurvey() {
		
		BaselineSurveyContactDto contact =  new BaselineSurveyContactDto();
		contact.setEmail("gc.atulbhanushali@godrejproperties.com");
		contact.setMobile("918356919019");
		contact.setFirstName("Atul");
		contact.setLastName("Bhanushali");
		contact.setSegmentCode("S90");
		contact.setTransactionDate("12/12/2019");
		contact.setField20("Prakash");

		BaselineSurveyContactDto contact2 =  new BaselineSurveyContactDto();
		contact2.setEmail("gc.atulbhanushali@godrejproperties.com");
		contact2.setMobile("918356919019");
		contact2.setFirstName("Atul");
		contact2.setLastName("Bhanushali");
		contact2.setSegmentCode("S90");
		contact2.setTransactionDate("12/12/2019");
		contact2.setField20("Vivek");
		List<BaselineSurveyContactDto> contacts=  new ArrayList<>();
		contacts.add(contact);
		contacts.add(contact2);
		rmSurveyApi.post(contacts,"PRE");
		return "OK";
	}

	@GetMapping("testReminderBaseline")
	public @ResponseBody ResponseDto testReminder() {
	
		BaselineSurveyReminderContactDto contact = new BaselineSurveyReminderContactDto();
		contact.setFirstName("Vivek");
		contact.setLastName("Birdi");
		contact.setSegmentCode("S90");
		contact.setEmail("vivek.birdi@novelerp.com");
		contact.setCustom20("Sapna");
		contact.setMobile("+917738915689");
		contact.setReminderNumber("1");
		return rmSurveyApi.postReminder(contact);

	}
	
	@GetMapping("triggerReminderBaseline")
	public @ResponseBody String triggerReminder(@RequestParam("projectid") String sfid) {
		ProjectDto project =projectService.getProject(sfid);
//		project.setSegmentCode("GPL");
		rmSurveyService.sendReminder(project);
		return "OK";
	}
	
	@GetMapping("sendReminderAllBaseline")
	public @ResponseBody String sendReminder() {
		rmSurveyService.sendReminderToAllRegions();
		return "OK";
	}
	 
	@PostMapping("/pr/sendMultiProjectSurveyBL")
	public @ResponseBody String sendMultiProjectSurveyBL(@RequestParam("projectSfid") String projectSfid, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		
		if(log.isInfoEnabled()) {
			log.info("%s %s","Baseline_", Calendar.getInstance().getTime());
		}
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formatDateTime = now.format(formatter);
        
        String instanceId = "GPL-baseline-survey-All-Onboard-"+formatDateTime;
		
		//List<ProjectDto> projectList =  projectService.getProjectsForScheduler();
		List<BaselineSurveyContactDto> contactsList = new ArrayList<BaselineSurveyContactDto>();
		
		if (projectSfid != null && !projectSfid.equals("null") && !projectSfid.equals("")) {
			String [] mf= projectSfid.split(",");
			
			for (int i=0;i<mf.length;i++){
				log.info("name {}",mf[i]);
				//contactsList = surveyService.sendSurveyWithScheduler(dto.getSfid(), null, null,instanceId);
				contactsList = rmSurveyService.sendMultiSurvey(mf[i],fromDate, toDate,instanceId);
				
				System.out.println("BL SEND Survey : " + mf[i] + " " + i);
			}
			
		}	
		
		/*for(ProjectDto dto:projectList) {
			log.info("name {}",dto.getName());
			//contactsList = surveyService.sendSurveyWithScheduler(dto.getSfid(), null, null,instanceId);
			contactsList = rmSurveyService.sendMultiSurvey(projectSfid,null,null,instanceId);
		}*/
		log.info("Entrys Size: {}",contactsList.size());
		if(contactsList.size()>0)
		{
			BaselineSurveyExcelHelper excelHelper = new BaselineSurveyExcelHelper();
			try {
				
				//UpdateBaselineSurveyField.updateBaselineData(contactsList);
				
				excelHelper.tutorialsToExcel(contactsList,instanceId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			springSftpController.sftpcon("D:\\Satheesh\\Projects\\Litmus World\\Baseline\\"+instanceId+".csv",AppConstants.LW_BASELINE_SURVEY_FOLDER_PATH);
		}
		
		return "";
	}
}
