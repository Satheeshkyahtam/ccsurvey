package com.godrej.surveys.handover.service;

import java.util.List;

import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.handover.dto.HandoverSurveyContactDto;
import com.godrej.surveys.handover.dto.HandoverSurveyReminderContactDto;

public interface HandoverSurveyAPIService {

	public ResponseDto post(List<HandoverSurveyContactDto> contacts);
	
	public ResponseDto postReminder(HandoverSurveyReminderContactDto contact);
}
