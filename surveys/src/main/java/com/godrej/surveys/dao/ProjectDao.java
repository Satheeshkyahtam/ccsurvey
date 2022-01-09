package com.godrej.surveys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.godrej.surveys.dto.ProjectDto;

@Mapper
public interface ProjectDao {
	
	@Select("Select distinct region__C region FROM " +  
			"salesforce.propstrength__projects__c WHERE region__c is not null "
			+ " ORDER BY region__C ASC")
	public List<String> getRegions();
	
	@Select("Select name,sfid,propstrength__project_code__c as segmentCode, region__c as region  FROM " + 
			"salesforce.propstrength__projects__c WHERE region__c =#{region}"
			+ "  ORDER BY name ASC")
	public List<ProjectDto> getProjects(String region);

	@Select("Select name,sfid,propstrength__project_code__c as segmentCode, region__c as region  FROM " + 
			"salesforce.propstrength__projects__c WHERE sfid in ( 'a1l6F000008DnniQAC','a1l6F000008fqcuQAA','a1l6F000002dTpoQAE')"
			+ "  ORDER BY name ASC")
	public List<ProjectDto> getProjectsTest();
	
	@Select("Select name,sfid,propstrength__project_code__c as segmentCode , region__c as region FROM " + 
			"salesforce.propstrength__projects__c WHERE sfid =#{projectSfid}"
			)
	public ProjectDto getProject(String projectSfid);
	
	@Select("Select name,sfid,propstrength__project_code__c as segmentCode, region__c as region  FROM " + 
//			"salesforce.propstrength__projects__c WHERE sfid in ( 'a1l6F000008DnniQAC')"
			" salesforce.propstrength__projects__c WHERE sfid not in ( 'a1l6F000008DnniQAC','a1l6F000008fqcuQAA','a1l6F000002dTpoQAE')"
			+ " ${dateWhereCondition} "
			+ " ORDER BY name ASC")
	public List<ProjectDto> getProjectsForScheduler(String dateWhereCondition);
	
	/* Added by A */
	@Select("Select name,sfid,propstrength__project_code__c as segmentCode, region__c as region  FROM " + 
			"salesforce.propstrength__projects__c WHERE sfid not in ( 'a1l6F000008DnniQAC','a1l6F000008fqcuQAA','a1l6F000002dTpoQAE') "
			+ " and Handover_Survey_Start_Date__c::date < now()::date and Handover_Survey_Start_Date__c is not null "
			+ " ORDER BY name ASC")
	public List<ProjectDto> getHOProjects(String region);
	/* END Added by A */
	
	/* Added by A */
	@Select("Select name,sfid,propstrength__project_code__c as segmentCode, region__c as region  FROM " + 
			//"salesforce.propstrength__projects__c WHERE sfid not in ( 'a1l6F000008DnniQAC','a1l6F000008fqcuQAA','a1l6F000002dTpoQAE') "
			"salesforce.propstrength__projects__c WHERE sfid not in ('a1l6F000008fqcuQAA','a1l6F000002dTpoQAE') "
			+ " and Baseline_Survey_Start_Date__c::date < now()::date and Baseline_Survey_Start_Date__c is not null "
			+ " ORDER BY name ASC")
	public List<ProjectDto> getBLProjects(String region);
	/* END Added by A */
}