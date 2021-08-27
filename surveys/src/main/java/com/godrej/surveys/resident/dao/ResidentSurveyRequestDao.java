package com.godrej.surveys.resident.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.resident.dto.ResidentSurveyContactDto;
import com.godrej.surveys.resident.dto.ResidentSurveyReminderContactDto;
import com.godrej.surveys.resident.sqlprovider.ResidentSurveySQLProvider;

@Mapper
public interface ResidentSurveyRequestDao {


	@SelectProvider(type = ResidentSurveySQLProvider.class, method = "getContactSQL")
	public Set<ResidentSurveyContactDto> getContacts(ProjectDto project);
	
	@SelectProvider(type = ResidentSurveySQLProvider.class, method = "getFirstReminderSQL")
	public Set<ResidentSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);
	
	@SelectProvider(type = ResidentSurveySQLProvider.class, method = "getSecondReminderSQL")
	public Set<ResidentSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);
	
	@SelectProvider(type = ResidentSurveySQLProvider.class, method = "getStatusUpdateQuery")
	public Integer updateStatus(ProjectDto project);
	
	@SelectProvider(type = ResidentSurveySQLProvider.class, method = "getPostStatusUpdateQuery")
	public Integer updateStatusPost(ProjectDto project);
	
	@SelectProvider(type = ResidentSurveySQLProvider.class, method = "getUpdateFirstReminderStatusSQL")
	public Integer updateFistReminderStaus(ResidentSurveyReminderContactDto contact);
	
	@SelectProvider(type = ResidentSurveySQLProvider.class, method = "getUpdateSecondReminderStatusSQL")
	public Integer updateSecondReminderStaus(ResidentSurveyReminderContactDto contact);

	@SelectProvider(type = ResidentSurveySQLProvider.class, method = "getClearingUpdateQuery")
	public Integer clearExtraUpdate(ProjectDto project);
}
