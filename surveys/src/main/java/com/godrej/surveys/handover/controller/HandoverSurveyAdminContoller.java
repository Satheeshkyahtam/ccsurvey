package com.godrej.surveys.handover.controller;

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
import com.godrej.surveys.handover.dto.HandoverSurveyContactDto;
import com.godrej.surveys.handover.service.HandoverSurveyService;
import com.godrej.surveys.service.ProjectService;

@Controller
public class HandoverSurveyAdminContoller {


	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private HandoverSurveyService surveyService;
	
	@GetMapping(value = "/pr/surveyAdminHOV")
	public ModelAndView openSurveyAdminPage() {
		ModelAndView view = new ModelAndView();
		try {
			List<String> regions = projectService.getRegions();
			view.addObject("regions", regions);
		}catch (Exception e) {
			log.error("Error", e);
		}
		view.setViewName("pro/survey/hov/handoverSurveyAdmin");
		return view;
	}

	@PostMapping("/pr/getContactsHOV")
	public @ResponseBody ResponseDto getContacts(@RequestParam("projectSfid") String projectSfid) {
		List<HandoverSurveyContactDto> contacts =  surveyService.getContacts(projectSfid,null,null);
		ResponseDto response = new ResponseDto(false, "");
		response.addData("contacts", contacts);
		response.addData("count", Integer.valueOf(contacts.size()));
		return response;
	}
	
	@PostMapping("/pr/sendSurveyHOV")
	public @ResponseBody ResponseDto sendSurvey(@RequestParam("projectSfid") String projectSfid
			) {
		ResponseDto response = null;
		try {
			response = surveyService.sendSurvey(projectSfid,null,null);
//			response =  new ResponseDto(true, "Not activated Yet");
		}catch (Exception e) {
			log.error("Error", e);
			response =  new ResponseDto(true, "Problem while sending Survey");
		}
		return response;
	}

}
