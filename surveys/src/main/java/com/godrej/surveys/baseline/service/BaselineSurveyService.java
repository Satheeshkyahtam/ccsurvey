package com.godrej.surveys.baseline.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.baseline.dto.BaselineSurveyContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderContactDto;
import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;

public interface BaselineSurveyService {

	public List<BaselineSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);

	public Set<BaselineSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<BaselineSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate);

	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
}
