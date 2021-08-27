package com.godrej.surveys.baseline.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.baseline.dto.BaselineBookingParam;
import com.godrej.surveys.baseline.dto.BaselineSurveyContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderContactDto;
import com.godrej.surveys.baseline.sqlprovider.BaselineSurveySQLProvider;
import com.godrej.surveys.dto.ProjectDto;

@Mapper
public interface BaselineSurveyRequestDao {


	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getContactSQL")
	public Set<BaselineSurveyContactDto> getContacts(ProjectDto project);
	
	@SelectProvider (type = BaselineSurveySQLProvider.class, method = "getContactQuery")
	public Set<BaselineSurveyContactDto> getParkedContacts(ProjectDto project);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getPrePossessionContactSQL")
	public Set<BaselineSurveyContactDto> getPrePossessionContacts(ProjectDto project);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getPostPossessionContactSQL")
	public Set<BaselineSurveyContactDto> getPostPossessionContacts(ProjectDto project);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getPrePossessionContactSQLQuery")
	public Set<BaselineSurveyContactDto> getPrePossessionParkedContacts(ProjectDto project);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getPostPossessionContactSQLQuery")
	public Set<BaselineSurveyContactDto> getPostPossessionParkedContacts(ProjectDto project);

	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getFirstReminderSQL")
	public Set<BaselineSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getSecondReminderSQL")
	public Set<BaselineSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getStatusUpdateQuery")
	public Integer updateStatus(ProjectDto project);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getPostStatusUpdateQuery")
	public Integer updateStatusPost(ProjectDto project);
	
	@UpdateProvider(type = BaselineSurveySQLProvider.class, method = "getBookingStatusUpdateQuery")
	public Integer updateStatusBookings(@Param("bookingParam") BaselineBookingParam bookingParam);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getUpdateFirstReminderStatusSQL")
	public Integer updateFistReminderStaus(BaselineSurveyReminderContactDto contact);
	
	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getUpdateSecondReminderStatusSQL")
	public Integer updateSecondReminderStaus(BaselineSurveyReminderContactDto contact);

	@SelectProvider(type = BaselineSurveySQLProvider.class, method = "getClearingUpdateQuery")
	public Integer clearExtraUpdate(ProjectDto project);
	
	@UpdateProvider(type = BaselineSurveySQLProvider.class, method = "getStatusUpdateFromContactLogsQuery")
	public Integer updateStatusFromContactLogs(ProjectDto project);

	@InsertProvider(type = BaselineSurveySQLProvider.class, method = "parkRecords")
	public Integer parkRecords(ProjectDto project);

}
