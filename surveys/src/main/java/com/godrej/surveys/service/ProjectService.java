package com.godrej.surveys.service;

import java.util.List;

import com.godrej.surveys.dto.ProjectDto;

public interface ProjectService {

	public List<String> getRegions();
	
	public List<ProjectDto> getProjects(String region);
	
	public ProjectDto getProject(String projectSfid);
	
	public List<ProjectDto> getProjectsForScheduler(String dateWhereCondition);
	
	public List<ProjectDto> getHOProjects(String region);
}
