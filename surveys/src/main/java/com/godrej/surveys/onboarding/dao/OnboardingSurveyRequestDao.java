package com.godrej.surveys.onboarding.dao;

import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyContactDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyReminderContactDto;
import com.godrej.surveys.onboarding.sqlprovider.OnboardingSurveySQLProvider;

@Mapper
public interface OnboardingSurveyRequestDao {


	/*
	 * @SelectProvider(type = OnboardingSurveySQLProvider.class, method =
	 * "getContactSQL")
	 */
	@SelectProvider(type = OnboardingSurveySQLProvider.class, method = "getContactQuery")
	public Set<OnboardingSurveyContactDto> getContacts(ProjectDto project);
	
	@SelectProvider(type = OnboardingSurveySQLProvider.class, method = "getFirstReminderSQL")
	public Set<OnboardingSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);
	
	@SelectProvider(type = OnboardingSurveySQLProvider.class, method = "getSecondReminderSQL")
	public Set<OnboardingSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);
	
	@SelectProvider(type = OnboardingSurveySQLProvider.class, method = "getStatusUpdateQuery")
	public Integer updateStatus(ProjectDto project);
	
	@SelectProvider(type = OnboardingSurveySQLProvider.class, method = "getPostStatusUpdateQuery")
	public Integer updateStatusPost(ProjectDto project);
	
	@SelectProvider(type = OnboardingSurveySQLProvider.class, method = "getUpdateFirstReminderStatusSQL")
	public Integer updateFistReminderStaus(OnboardingSurveyReminderContactDto contact);
	
	@SelectProvider(type = OnboardingSurveySQLProvider.class, method = "getUpdateSecondReminderStatusSQL")
	public Integer updateSecondReminderStaus(OnboardingSurveyReminderContactDto contact);

	@SelectProvider(type = OnboardingSurveySQLProvider.class, method = "getClearingUpdateQuery")
	public Integer clearExtraUpdate(ProjectDto project);
	
	@UpdateProvider(type = OnboardingSurveySQLProvider.class, method = "getStatusUpdateFromContactLogsQuery")
	public Integer updateStatusFromContactLogs(ProjectDto project);
	
	@InsertProvider(type = OnboardingSurveySQLProvider.class, method = "parkRecords")
	public Integer parkRecords (ProjectDto project);
}
