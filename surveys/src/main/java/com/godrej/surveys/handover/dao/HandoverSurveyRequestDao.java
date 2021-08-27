package com.godrej.surveys.handover.dao;

import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.handover.dto.HandoverSurveyContactDto;
import com.godrej.surveys.handover.dto.HandoverSurveyReminderContactDto;
import com.godrej.surveys.handover.sqlprovider.HandoverSurveySQLProvider;

@Mapper
public interface HandoverSurveyRequestDao {


	/*
	 * @SelectProvider(type = HandoverSurveySQLProvider.class, method =
	 * "getContactSQL")
	 */
	@SelectProvider(type = HandoverSurveySQLProvider.class, method = "getContactQuery")
	public Set<HandoverSurveyContactDto> getContacts(ProjectDto project);
	
	@SelectProvider(type = HandoverSurveySQLProvider.class, method = "getFirstReminderSQL")
	public Set<HandoverSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);
	
	@SelectProvider(type = HandoverSurveySQLProvider.class, method = "getSecondReminderSQL")
	public Set<HandoverSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);
	
	@SelectProvider(type = HandoverSurveySQLProvider.class, method = "getStatusUpdateQuery")
	public Integer updateStatus(ProjectDto project);
	
	@UpdateProvider(type = HandoverSurveySQLProvider.class, method = "getStatusUpdateFromContactLogsQuery")
	public Integer updateStatusFromContactLogs(ProjectDto project);
	
	@SelectProvider(type = HandoverSurveySQLProvider.class, method = "getPostStatusUpdateQuery")
	public Integer updateStatusPost(ProjectDto project);
	
	@SelectProvider(type = HandoverSurveySQLProvider.class, method = "getUpdateFirstReminderStatusSQL")
	public Integer updateFistReminderStaus(HandoverSurveyReminderContactDto contact);
	
	@SelectProvider(type = HandoverSurveySQLProvider.class, method = "getUpdateSecondReminderStatusSQL")
	public Integer updateSecondReminderStaus(HandoverSurveyReminderContactDto contact);

	@SelectProvider(type = HandoverSurveySQLProvider.class, method = "getClearingUpdateQuery")
	public Integer clearExtraUpdate(ProjectDto project);
	
	@InsertProvider(type = HandoverSurveySQLProvider.class, method = "parkRecords")
	public Integer parkRecords (ProjectDto project);
}
