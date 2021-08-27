package com.godrej.surveys.sitevisit.service;

import java.util.List;

import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.sitevisit.dto.SiteVisitSurveyContactDto;
import com.godrej.surveys.sitevisit.dto.SiteVisitSurveyReminderContactDto;

public interface SiteVisitSurveyAPIService {

	public ResponseDto post(List<SiteVisitSurveyContactDto> contacts);
	
	public ResponseDto postReminder(SiteVisitSurveyReminderContactDto contact);
}
