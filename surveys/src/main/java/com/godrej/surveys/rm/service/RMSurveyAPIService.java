package com.godrej.surveys.rm.service;

import java.util.List;

import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.rm.dto.RMSurveyContactDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderContactDto;

public interface RMSurveyAPIService {

	public ResponseDto post(List<RMSurveyContactDto> contacts);
	
	public ResponseDto postReminder(RMSurveyReminderContactDto contact);
}
