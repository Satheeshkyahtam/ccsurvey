package com.godrej.surveys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godrej.surveys.dao.ProjectDao;
import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	private Logger log= LoggerFactory.getLogger(getClass());

	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public List<String> getRegions() {
		log.info("Getting Regions");
		try {
			return projectDao.getRegions();
		}catch (Exception e) {
			log.error("Error", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<ProjectDto> getProjects(String region) {
		try{
			/*return projectDao.getProjectsTest();*/
			 return projectDao.getProjects(region); 
		}catch (Exception e) {
			log.error("Error", e);
		}
		return new ArrayList<>();
	}
	@Override
	public ProjectDto getProject(String projectSfid) {
		try {
			return projectDao.getProject(projectSfid);
		}catch (Exception e) {
			log.error("Error",e);
		}
		return null;
	}

	@Override
	public List<ProjectDto> getProjectsForScheduler(String dateWhereCondition) {
		// TODO Auto-generated method stub
		try {
			return projectDao.getProjectsForScheduler(dateWhereCondition);
		}catch (Exception e) {
			log.error("Error",e);
		}
		return null;
	}
	
	
	/* Added by A */
	@Override
	public List<ProjectDto> getHOProjects(String region) {
		try{
			/*return projectDao.getProjectsTest();*/
			 return projectDao.getHOProjects(region); 
		}catch (Exception e) {
			log.error("Error", e);
		}
		return new ArrayList<>();
	}
	/* END Added by A */
	
	/* Added by A */
	@Override
	public List<ProjectDto> getBLProjects(String region) {
		try{
			/*return projectDao.getProjectsTest();*/
			 return projectDao.getBLProjects(region); 
		}catch (Exception e) {
			log.error("Error", e);
		}
		return new ArrayList<>();
	}
	/* END Added by A */
}
