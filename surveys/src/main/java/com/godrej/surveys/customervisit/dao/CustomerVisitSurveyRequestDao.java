package com.godrej.surveys.customervisit.dao;

import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyContactDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderContactDto;
import com.godrej.surveys.customervisit.sqlprovider.CustomerVisitSurveySQLProvider;
import com.godrej.surveys.dto.ProjectDto;

@Mapper
public interface CustomerVisitSurveyRequestDao {


	/*
	 * @SelectProvider(type = CustomerVisitSurveySQLProvider.class, method =
	 * "getContactSQL") public Set<CustomerVisitSurveyContactDto>
	 * getContacts(ProjectDto project);
	 */
	
	@SelectProvider(type = CustomerVisitSurveySQLProvider.class, method = "getContactQuery")
	public Set<CustomerVisitSurveyContactDto> getContacts(ProjectDto project);
	
	@SelectProvider(type = CustomerVisitSurveySQLProvider.class, method = "getFirstReminderSQL")
	public Set<CustomerVisitSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);
	
	@SelectProvider(type = CustomerVisitSurveySQLProvider.class, method = "getSecondReminderSQL")
	public Set<CustomerVisitSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);
	
	@UpdateProvider(type = CustomerVisitSurveySQLProvider.class, method = "getStatusUpdateQuery")
	public Integer updateStatus(ProjectDto project);
	
	@UpdateProvider(type = CustomerVisitSurveySQLProvider.class, method = "getPostStatusUpdateQuery")
	public Integer updateStatusPost(ProjectDto project);
	
	@UpdateProvider(type = CustomerVisitSurveySQLProvider.class, method = "getUpdateFirstReminderStatusSQL")
	public Integer updateFistReminderStaus(CustomerVisitSurveyReminderContactDto contact);
	
	@UpdateProvider(type = CustomerVisitSurveySQLProvider.class, method = "getUpdateSecondReminderStatusSQL")
	public Integer updateSecondReminderStaus(CustomerVisitSurveyReminderContactDto contact);

	@SelectProvider(type = CustomerVisitSurveySQLProvider.class, method = "getClearingUpdateQuery")
	public Integer clearExtraUpdate(ProjectDto project);
	
	@UpdateProvider(type = CustomerVisitSurveySQLProvider.class, method = "getStatusUpdateFromContactLogsQuery")
	public Integer updateStatusFromContactLogs(ProjectDto project);
	
	@InsertProvider(type = CustomerVisitSurveySQLProvider.class, method = "parkRecords")
	public Integer parkRecords (ProjectDto project);
}
