package com.godrej.surveys.customervisit.service;

import java.util.List;

import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyContactDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderContactDto;
import com.godrej.surveys.dto.ResponseDto;

public interface CustomerVisitSurveyAPIService {

	public ResponseDto post(List<CustomerVisitSurveyContactDto> contacts);
	
	public ResponseDto postReminder(CustomerVisitSurveyReminderContactDto contact);
}
