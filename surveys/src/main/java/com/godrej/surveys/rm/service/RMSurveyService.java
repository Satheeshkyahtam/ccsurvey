package com.godrej.surveys.rm.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.rm.dto.RMSurveyContactDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderContactDto;

public interface RMSurveyService {

	public List<RMSurveyContactDto> getContacts(String projectSfid);

	public Set<RMSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<RMSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid);

	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
	
	public Set<RMSurveyReminderContactDto> getContactsForFirstReminderDyno(ProjectDto project);
	
	public Set<RMSurveyReminderContactDto> getContactsForSecondReminderDyno(ProjectDto project);
	
	public ResponseDto sendFirstReminderDyno(ProjectDto project);
	
	public ResponseDto sendSecondReminderDyno(ProjectDto project);
}
