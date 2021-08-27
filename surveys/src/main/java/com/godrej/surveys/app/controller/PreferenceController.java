package com.godrej.surveys.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.godrej.surveys.dto.Preference;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.service.PreferenceService;

@Controller
public class PreferenceController {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PreferenceService preferenceService;

	@PostMapping("/pr/submitPreference")
	public @ResponseBody ResponseDto insert(@RequestBody Preference preference) {
		
		ResponseDto response =  new ResponseDto(false, "Nothing to do! Please change the data!");
		try {
			Integer id =  preference.getSurveyPreferenceId();	
			if(id == null || id ==0) {
				Integer count = preferenceService.insert(preference);
				response = new ResponseDto(false, "Inserted Successfully");
				response.addData("preferenceId", preference.getSurveyPreferenceId());
			}else {
				Integer updateCount = preferenceService.update(preference);
				response = new ResponseDto(false, "udpated Successfully");
				response.addData("updateCount", updateCount);
				response.addData("preferenceId", preference.getSurveyPreferenceId());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new ResponseDto(true, e.getMessage());
		}
		
		return response;
	}
		
	@PostMapping("/getPreference")
	public @ResponseBody ResponseDto get(@RequestBody Preference preference) {
		
		ResponseDto response =  null;
		try {
			Preference record = preferenceService.select(preference);
			response = new ResponseDto(false, "Inserted Successfully");
			response.addData("preference", record);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new ResponseDto(true, e.getMessage());
		}
		
		return response;
	}
}
