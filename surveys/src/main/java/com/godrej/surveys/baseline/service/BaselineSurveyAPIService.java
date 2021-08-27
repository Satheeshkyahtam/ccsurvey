package com.godrej.surveys.baseline.service;

import java.util.List;

import com.godrej.surveys.baseline.dto.BaselineSurveyContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderContactDto;
import com.godrej.surveys.dto.ResponseDto;

public interface BaselineSurveyAPIService {

	public ResponseDto post(List<BaselineSurveyContactDto> contacts, String type);
	
	public ResponseDto postReminder(BaselineSurveyReminderContactDto contact);
}
