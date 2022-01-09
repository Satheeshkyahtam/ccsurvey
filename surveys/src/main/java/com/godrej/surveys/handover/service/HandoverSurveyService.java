package com.godrej.surveys.handover.service;

import java.util.List;
import java.util.Set;

import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.handover.dto.HandoverSurveyContactDto;
import com.godrej.surveys.handover.dto.HandoverSurveyReminderContactDto;

public interface HandoverSurveyService {

	public List<HandoverSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate);

	public Set<HandoverSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project);

	public Set<HandoverSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project);

	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate,String instanceId);
	public List<HandoverSurveyContactDto> sendSurveyWithScheduler(String projectSfid, String fromDate, String toDate, String instanceId);

	public ResponseDto sendReminder(ProjectDto project);

	public void sendReminderRegionWise(List<String> regions);

	public void sendReminderToAllRegions();
}
