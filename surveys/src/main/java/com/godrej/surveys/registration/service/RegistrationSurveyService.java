package com.godrej.surveys.registration.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.registration.dto.RegistrationSurveyContactDto;
import com.godrej.surveys.registration.dto.RegistrationSurveyReminderContactDto;

public interface RegistrationSurveyService {

	public List<RegistrationSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);

	public Set<RegistrationSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<RegistrationSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate);

	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
}
