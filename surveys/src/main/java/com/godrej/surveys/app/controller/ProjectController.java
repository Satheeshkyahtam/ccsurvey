package com.godrej.surveys.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.service.ProjectService;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping(value="getProjectList")
	public @ResponseBody ResponseDto getProjectList(@RequestParam ("region") String region) {
		ResponseDto response =null;
		try {
			List<ProjectDto> projects=projectService.getProjects(region);
			response =  new ResponseDto(false, "");
			response.addData("projects", projects);
		}catch (Exception e) {
			response =  new ResponseDto(true, e.getMessage());
		}
		
		return response;
	}
	

	@GetMapping(value="getProjectListGET")
	public @ResponseBody ResponseDto projectList(@RequestParam ("region") String region) {
		ResponseDto response =null;
		try {
			List<ProjectDto> projects=projectService.getProjects(region);
			response =  new ResponseDto(false, "");
			response.addData("projects", projects);
		}catch (Exception e) {
			response =  new ResponseDto(true, e.getMessage());
		}
		
		return response;
	}

}
