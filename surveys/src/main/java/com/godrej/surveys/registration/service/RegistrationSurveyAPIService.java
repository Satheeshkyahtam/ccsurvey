package com.godrej.surveys.registration.service;

import java.util.List;

import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.registration.dto.RegistrationSurveyContactDto;
import com.godrej.surveys.registration.dto.RegistrationSurveyReminderContactDto;

public interface RegistrationSurveyAPIService {

	public ResponseDto post(List<RegistrationSurveyContactDto> contacts);
	
	public ResponseDto postReminder(RegistrationSurveyReminderContactDto contact);
}
