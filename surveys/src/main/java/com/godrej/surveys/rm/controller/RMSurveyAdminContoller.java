package com.godrej.surveys.rm.controller;

import java.util.ArrayList;
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

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.rm.dto.RMSurveyContactDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderContactDto;
import com.godrej.surveys.rm.service.RMSurveyAPIService;
import com.godrej.surveys.rm.service.RMSurveyService;
import com.godrej.surveys.service.ProjectService;
import com.godrej.surveys.util.CommonUtil;

@Controller
public class RMSurveyAdminContoller {


	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private RMSurveyAPIService rmSurveyApi;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private RMSurveyService rmSurveyService;
	
	@GetMapping(value = "/pr/surveyAdmin")
	public ModelAndView openSurveyAdminPage() {
		ModelAndView view = new ModelAndView();
		try {
			List<String> regions = projectService.getRegions();
			view.addObject("regions", regions);
		}catch (Exception e) {
			log.error("Error", e);
		}
		view.setViewName("pro/survey/rm/surveyAdmin");
		return view;
	}

	@PostMapping("/pr/getContactsRM")
	public @ResponseBody ResponseDto getContacts(@RequestParam("projectSfid") String projectSfid ) {
		List<RMSurveyContactDto> contacts =  rmSurveyService.getContacts(projectSfid);
		ResponseDto response = new ResponseDto(false, "");
		response.addData("contacts", contacts);
		response.addData("count", Integer.valueOf(contacts.size()));
		return response;
	}
	
	@PostMapping("/pr/sendSurveyRM")
	public @ResponseBody ResponseDto sendSurvey(@RequestParam("projectSfid") String projectSfid) {
		ResponseDto response = null;
		try {
			response = rmSurveyService.sendSurvey(projectSfid);
		}catch (Exception e) {
			response =  new ResponseDto(true, "Problem while sending Survey");
			log.error(e.getMessage(),e);
		}
		return response;
	}
	@GetMapping(value = "testRMSurvey")
	public String testSurvey() {
		
		RMSurveyContactDto contact =  new RMSurveyContactDto();
		contact.setEmail("gc.atulbhanushali@godrejproperties.com");
		contact.setMobile("918356919019");
		contact.setFirstName("Atul");
		contact.setLastName("Bhanushali");
		contact.setSegmentCode("S90");
		contact.setTransactionDate("12/12/2019");
		contact.setField20("Prakash");

		RMSurveyContactDto contact2 =  new RMSurveyContactDto();
		contact2.setEmail("gc.atulbhanushali@godrejproperties.com");
		contact2.setMobile("918356919019");
		contact2.setFirstName("Atul");
		contact2.setLastName("Bhanushali");
		contact2.setSegmentCode("S90");
		contact2.setTransactionDate("12/12/2019");
		contact2.setField20("Vivek");
		List<RMSurveyContactDto> contacts=  new ArrayList<>();
		contacts.add(contact);
		contacts.add(contact2);
		rmSurveyApi.post(contacts);
		return "OK";
	}

	@GetMapping("testReminder")
	public @ResponseBody ResponseDto testReminder() {
	
		RMSurveyReminderContactDto contact = new RMSurveyReminderContactDto();
		contact.setFirstName("Vivek");
		contact.setLastName("Birdi");
		contact.setSegmentCode("S90");
		contact.setEmail("vivek.birdi@novelerp.com");
		contact.setCustom20("Sapna");
		contact.setMobile("+917738915689");
		contact.setReminderNumber("1");
		return rmSurveyApi.postReminder(contact);

	}
	
	@GetMapping("/pr/triggerReminder")
	public @ResponseBody String triggerReminder(@RequestParam("projectid") String sfid,@RequestParam( value = "segmentCode", required = false) String segmentCode
			,@RequestParam("surveyId") String surveyId ,@RequestParam("intervalP") int intervalP ,@RequestParam("intervalP2") int intervalP2
			,@RequestParam("remNo") int reminderNo) {
		
		ProjectDto project =projectService.getProject(sfid);
		if(!CommonUtil.isStringEmpty(segmentCode)) {
			project.setSegmentCode(segmentCode);
		}
		if(!CommonUtil.isStringEmpty(surveyId)) {
			project.setSurveyId(surveyId);
		}else {
			project.setSurveyId("6754624");
		}
		project.setIntervalP(intervalP);
		project.setIntervalP2(intervalP2);
		if(reminderNo ==1) {
			rmSurveyService.sendFirstReminderDyno(project);
		}else if(reminderNo == 2) {
			rmSurveyService.sendSecondReminderDyno(project);
		}
		return "OK";
	}
	
	@GetMapping("sendReminderAll")
	public @ResponseBody String sendReminder() {
		rmSurveyService.sendReminderToAllRegions();
		return "OK";
	}
}
