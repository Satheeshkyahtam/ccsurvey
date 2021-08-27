package com.godrej.surveys.onboarding.service;

import java.util.List;

import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyContactDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyReminderContactDto;

public interface OnboardingSurveyAPIService {

	public ResponseDto post(List<OnboardingSurveyContactDto> contacts);
	
	public ResponseDto postReminder(OnboardingSurveyReminderContactDto contact);
}
