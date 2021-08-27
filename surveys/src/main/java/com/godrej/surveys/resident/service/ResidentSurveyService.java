package com.godrej.surveys.resident.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.resident.dto.ResidentSurveyContactDto;
import com.godrej.surveys.resident.dto.ResidentSurveyReminderContactDto;

public interface ResidentSurveyService {

	public List<ResidentSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);

	public Set<ResidentSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<ResidentSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate);

	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
}
