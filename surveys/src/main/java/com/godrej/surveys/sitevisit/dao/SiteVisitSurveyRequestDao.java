package com.godrej.surveys.sitevisit.dao;

import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.sitevisit.dto.SiteVisitSurveyContactDto;
import com.godrej.surveys.sitevisit.dto.SiteVisitSurveyReminderContactDto;
import com.godrej.surveys.sitevisit.sqlprovider.SiteVisitSurveySQLProvider;

@Mapper
public interface SiteVisitSurveyRequestDao {


	/*
	 * @SelectProvider(type = SiteVisitSurveySQLProvider.class, method =
	 * "getContactSQL")
	 */
	@SelectProvider(type = SiteVisitSurveySQLProvider.class, method = "getContactQuery")
	public Set<SiteVisitSurveyContactDto> getContacts(ProjectDto project);
	
	@SelectProvider(type = SiteVisitSurveySQLProvider.class, method = "getFirstReminderSQL")
	public Set<SiteVisitSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);
	
	@SelectProvider(type = SiteVisitSurveySQLProvider.class, method = "getSecondReminderSQL")
	public Set<SiteVisitSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);
	
	@SelectProvider(type = SiteVisitSurveySQLProvider.class, method = "getStatusUpdateQuery")
	public Integer updateStatus(ProjectDto project);
	
	@SelectProvider(type = SiteVisitSurveySQLProvider.class, method = "getPostStatusUpdateQuery")
	public Integer updateStatusPost(ProjectDto project);
	
	@SelectProvider(type = SiteVisitSurveySQLProvider.class, method = "getUpdateFirstReminderStatusSQL")
	public Integer updateFistReminderStaus(SiteVisitSurveyReminderContactDto contact);
	
	@SelectProvider(type = SiteVisitSurveySQLProvider.class, method = "getUpdateSecondReminderStatusSQL")
	public Integer updateSecondReminderStaus(SiteVisitSurveyReminderContactDto contact);

	@SelectProvider(type = SiteVisitSurveySQLProvider.class, method = "getClearingUpdateQuery")
	public Integer clearExtraUpdate(ProjectDto project);
	
	@UpdateProvider(type = SiteVisitSurveySQLProvider.class, method = "getStatusUpdateFromContactLogsQuery")
	public Integer updateStatusFromContactLogs(ProjectDto project);
	
	@InsertProvider(type = SiteVisitSurveySQLProvider.class, method = "parkRecords")
	public Integer parkRecords (ProjectDto project);
}
