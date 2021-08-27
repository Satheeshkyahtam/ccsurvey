package com.godrej.surveys.registration.dao;

import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.registration.dto.RegistrationSurveyContactDto;
import com.godrej.surveys.registration.dto.RegistrationSurveyReminderContactDto;
import com.godrej.surveys.registration.sqlprovider.RegistrationSurveySQLProvider;

@Mapper
public interface RegistrationSurveyRequestDao {


	/*
	 * @SelectProvider(type = RegistrationSurveySQLProvider.class, method =
	 * "getContactSQL")
	 */
	@SelectProvider(type = RegistrationSurveySQLProvider.class, method = "getContactQuery")
	public Set<RegistrationSurveyContactDto> getContacts(ProjectDto project);
	
	@SelectProvider(type = RegistrationSurveySQLProvider.class, method = "getFirstReminderSQL")
	public Set<RegistrationSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);
	
	@SelectProvider(type = RegistrationSurveySQLProvider.class, method = "getSecondReminderSQL")
	public Set<RegistrationSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);
	
	@SelectProvider(type = RegistrationSurveySQLProvider.class, method = "getStatusUpdateQuery")
	public Integer updateStatus(ProjectDto project);
	
	@SelectProvider(type = RegistrationSurveySQLProvider.class, method = "getPostStatusUpdateQuery")
	public Integer updateStatusPost(ProjectDto project);
	
	@SelectProvider(type = RegistrationSurveySQLProvider.class, method = "getUpdateFirstReminderStatusSQL")
	public Integer updateFistReminderStaus(RegistrationSurveyReminderContactDto contact);
	
	@SelectProvider(type = RegistrationSurveySQLProvider.class, method = "getUpdateSecondReminderStatusSQL")
	public Integer updateSecondReminderStaus(RegistrationSurveyReminderContactDto contact);

	@SelectProvider(type = RegistrationSurveySQLProvider.class, method = "getClearingUpdateQuery")
	public Integer clearExtraUpdate(ProjectDto project);
	
	@UpdateProvider(type = RegistrationSurveySQLProvider.class, method = "getStatusUpdateFromContactLogsQuery")
	public Integer updateStatusFromContactLogs(ProjectDto project);
	
	@InsertProvider(type = RegistrationSurveySQLProvider.class, method = "parkRecords")
	public Integer parkRecords (ProjectDto project);

}
