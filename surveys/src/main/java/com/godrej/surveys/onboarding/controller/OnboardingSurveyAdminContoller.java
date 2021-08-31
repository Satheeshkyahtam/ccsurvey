package com.godrej.surveys.onboarding.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyContactDto;
import com.godrej.surveys.onboarding.service.OnboardingSurveyService;
import com.godrej.surveys.service.ProjectService;

@Controller
public class OnboardingSurveyAdminContoller {


	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private OnboardingSurveyService surveyService;
	
	@GetMapping(value = "/pr/surveyAdminONB")
	public ModelAndView openSurveyAdminPage() {
		ModelAndView view = new ModelAndView();
		try {
			List<String> regions = projectService.getRegions();
			view.addObject("regions", regions);
		}catch (Exception e) {
			log.error("Error", e);
		}
		view.setViewName("pro/survey/onb/onboardingSurveyAdmin");
		return view;
	}

	@PostMapping("/pr/getContactsONB")
	public @ResponseBody ResponseDto getContacts(@RequestParam("projectSfid") String projectSfid) {
		List<OnboardingSurveyContactDto> contacts =  surveyService.getContacts(projectSfid,null,null);
		ResponseDto response = new ResponseDto(false, "");
		response.addData("contacts", contacts);
		response.addData("count", Integer.valueOf(contacts.size()));
		return response;
	}
	
	@PostMapping("/pr/sendSurveyONB")
	public @ResponseBody ResponseDto sendSurvey(@RequestParam("projectSfid") String projectSfid) {
		ResponseDto response = null;
		try {
			LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
	        String formatDateTime = now.format(formatter);
	        String instanceId = "GPL-custonboarding-survey-PRwise-Onboard-"+formatDateTime;
			response = surveyService.sendSurvey(projectSfid,null,null,instanceId);
		}catch (Exception e) {
			log.error("Error", e);
			response =  new ResponseDto(true, "Problem while sending Survey");
		}
		return response;
	}

}
