package com.godrej.surveys.baseline.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.baseline.dto.BaselineSurveyContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderContactDto;
import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyContactDto;

public interface BaselineSurveyService {

	//public List<BaselineSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);
	public List<BaselineSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);
	
	public Set<BaselineSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<BaselineSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate, String instanceId);
	
	public List<BaselineSurveyContactDto> sendMultiSurvey(String projectSfid, String fromDate, String toDate, String instanceId);
	
	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
}
