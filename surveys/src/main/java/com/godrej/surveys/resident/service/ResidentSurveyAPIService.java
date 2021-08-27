package com.godrej.surveys.resident.service;

import java.util.List;

import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.resident.dto.ResidentSurveyContactDto;
import com.godrej.surveys.resident.dto.ResidentSurveyReminderContactDto;

public interface ResidentSurveyAPIService {

	public ResponseDto post(List<ResidentSurveyContactDto> contacts);
	
	public ResponseDto postReminder(ResidentSurveyReminderContactDto contact);
}
