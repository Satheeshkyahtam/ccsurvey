package com.godrej.surveys.rm.dao;

import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.rm.dto.RMSurveyContactDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderContactDto;
import com.godrej.surveys.rm.sqlprovider.RMSurveySQLProvider;

@Mapper
public interface SurveyRequestDao {

	
	@SelectProvider(type = RMSurveySQLProvider.class, method = "getContactSQL")	 
	public Set<RMSurveyContactDto> getContacts(ProjectDto project);

	@SelectProvider (type = RMSurveySQLProvider.class, method = "getContactQuery")
	public Set<RMSurveyContactDto> getParkedContacts(ProjectDto project);

	@SelectProvider(type = RMSurveySQLProvider.class, method = "getFirstReminderSQL")
	public Set<RMSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	@SelectProvider(type = RMSurveySQLProvider.class, method = "getFirstReminderSQLDyno")
	public Set<RMSurveyReminderContactDto> getContactsForFirstReminderDyno(ProjectDto project);

	@SelectProvider(type = RMSurveySQLProvider.class, method = "getSecondReminderSQL")
	public Set<RMSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	@SelectProvider(type = RMSurveySQLProvider.class, method = "getSecondReminderSQLDyno")
	public Set<RMSurveyReminderContactDto> getContactsForSecondReminderDyno(ProjectDto project);

	@SelectProvider(type = RMSurveySQLProvider.class, method = "getStatusUpdateQuery")
	public Integer updateStatus(ProjectDto project);

	@SelectProvider(type = RMSurveySQLProvider.class, method = "getUpdateFirstReminderStatusSQL")
	public Integer updateFistReminderStaus(RMSurveyReminderContactDto contact);

	@SelectProvider(type = RMSurveySQLProvider.class, method = "getUpdateSecondReminderStatusSQL")
	public Integer updateSecondReminderStaus(RMSurveyReminderContactDto contact);

	@SelectProvider(type = RMSurveySQLProvider.class, method = "getClearingUpdateQuery")
	public Integer clearExtraUpdate(ProjectDto project);

	@UpdateProvider(type = RMSurveySQLProvider.class, method = "getStatusUpdateFromContactLogsQuery")
	public Integer updateStatusFromContactLogs(ProjectDto project);

	@InsertProvider(type = RMSurveySQLProvider.class, method = "parkRecords")
	public Integer parkRecords(ProjectDto project);

}
