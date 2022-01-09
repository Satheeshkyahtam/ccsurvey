package com.godrej.surveys.sitevisit.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.sitevisit.dto.SiteVisitSurveyContactDto;
import com.godrej.surveys.sitevisit.dto.SiteVisitSurveyReminderContactDto;

public interface SiteVisitSurveyService {

	public List<SiteVisitSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);

	public Set<SiteVisitSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<SiteVisitSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate, String instanceId);
	
	public List<SiteVisitSurveyContactDto> sendSurveyWithScheduler(String projectSfid, String fromDate, String toDate, String instanceId);

	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
}
