package com.godrej.surveys.customervisit.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyContactDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderContactDto;
import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;

public interface CustomerVisitSurveyService {

	public List<CustomerVisitSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);

	public Set<CustomerVisitSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<CustomerVisitSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate);

	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
}
