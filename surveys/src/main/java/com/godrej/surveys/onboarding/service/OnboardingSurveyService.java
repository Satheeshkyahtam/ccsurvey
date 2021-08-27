package com.godrej.surveys.onboarding.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyContactDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyReminderContactDto;

public interface OnboardingSurveyService {

	public List<OnboardingSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);

	public Set<OnboardingSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<OnboardingSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate, String instanceId);

	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
	
	public List<OnboardingSurveyContactDto> sendSurveyWithScheduler(String projectSfid, String fromDate, String toDate, String instanceId);
	
}
