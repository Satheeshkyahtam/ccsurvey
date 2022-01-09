package com.godrej.surveys.handover.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godrej.surveys.dto.APILoggerDto;
import com.godrej.surveys.dto.ErrorDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.handover.dto.HandoverSurveyContactDto;
import com.godrej.surveys.handover.dto.HandoverSurveyReminderContactDto;
import com.godrej.surveys.handover.dto.HandoverSurveyReminderRequestDto;
import com.godrej.surveys.handover.dto.HandoverSurveyReminderResponseWrapperDto;
import com.godrej.surveys.handover.dto.HandoverSurveyRequestDto;
import com.godrej.surveys.handover.dto.HandoverSurveyResponseWrapperDto;
import com.godrej.surveys.handover.service.HandoverSurveyAPIService;
import com.godrej.surveys.service.APILoggerService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.DateUtil;

@Service
public class HandoverSurveyRestServiceImpl implements HandoverSurveyAPIService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private APILoggerService apiLoggerService;

	private Logger log = LoggerFactory.getLogger(getClass());
	private static final String IMPORT_CONTACT_URL = "https://www.questionpro.com/a/api/questionpro.cx.importTransaction?apiKey=";
	private static final String SEND_REMINDER_URL = "https://www.questionpro.com/a/api/questionpro.cx.sendReminderForEmail?apiKey=";
	private static final String SURVEY_ID = "6119246";
	private static final String INVITATION_TEMPLATE_ID = "470270";
	private static final String SMS_TEMPLATE_ID = "164";

	/*
	 * private static final String POST_POSSESSION_SURVEY_ID="6356252"; private
	 * static final String FIRST_SMS_TEMPLATE_ID = "149"; private static final
	 * String SECOND_SMS_TEMPLATE_ID = "491"; private static final String
	 * POST_INVITATION_TEMPLATE_ID="533978"; private static final String
	 * SECOND_INVITATION_TEMPLATE_ID="736741"; private static final String
	 * SECOND_POST_INVITATION_TEMPLATE_ID="736741";
	 */
	
	@Override
	public ResponseDto post(List<HandoverSurveyContactDto> contacts) {

		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");
		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		HandoverSurveyRequestDto request = getRequestDto();
		request.setContacts(contacts);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {

			String apiResponse = restTemplate.postForObject(url, request, String.class);
			HandoverSurveyResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					HandoverSurveyResponseWrapperDto.class);
			response = new ResponseDto(false, "Success");
			response.addData("rmSurveyResponse", wrappedResponse);
			responseLog.append(apiResponse);
		} catch (Exception e) {
			log.error("Error", e);
			String message = e.getMessage();
			ErrorDto error = new ErrorDto(AppConstants.SURVEY_API_ERROR_CODE, message);
			response = new ResponseDto(true, "Problem in callin API", error);
			responseLog.append(message);
		}
		APILoggerDto apiLogger = new APILoggerDto(requestLog.toString(), responseLog.toString());
		apiLoggerService.insert(apiLogger);
		return response;
	}

	private HandoverSurveyRequestDto getRequestDto() {
		HandoverSurveyRequestDto request = new HandoverSurveyRequestDto();
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSendEmail("true");
		request.setSendSMS("true");
		request.setSmsTemplateID(SMS_TEMPLATE_ID);
		return request;

	}

	private String getRequestJSON(Object request) {
		try {
			return mapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			log.error("Error", e);
			return e.getMessage();
		}
	}

	public static void main(String[] args) {

		Logger log = LoggerFactory.getLogger(HandoverSurveyRestServiceImpl.class);

		HandoverSurveyRequestDto request = new HandoverSurveyRequestDto();
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSendEmail("true");
		request.setSendSMS("true");
		request.setSmsTemplateID(SMS_TEMPLATE_ID);

		HandoverSurveyContactDto contact = new HandoverSurveyContactDto();
		contact.setUser_email("vivekbirdi@gmail.com");
		contact.setUser_phone("917738915689");
		contact.setFirstName("Vivek");
		contact.setLastName("Birdi");
		contact.setSegmentCode("S90");
		contact.setTransactionDate("12/12/2019");

		List<HandoverSurveyContactDto> contacts = new ArrayList<>();
		contacts.add(contact);
		request.setContacts(contacts);

		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		String response = new RestTemplate().postForObject(url, request, String.class);
		log.info(response);
	}

	@Override
	public ResponseDto postReminder(HandoverSurveyReminderContactDto contact) {
		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");

		String url = SEND_REMINDER_URL + AppConstants.API_KEY;
		HandoverSurveyReminderRequestDto request = getReminderRequestDto();
		request.setContact(contact);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {
			String apiResponse = restTemplate.postForObject(url, request, String.class);
			HandoverSurveyReminderResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					HandoverSurveyReminderResponseWrapperDto.class);
			response = new ResponseDto(false, "Success");
			response.addData("rmSurveyReminderResponse", wrappedResponse);
			responseLog.append(apiResponse);
		} catch (Exception e) {
			log.error("Error", e);
			String message = e.getMessage();
			ErrorDto error = new ErrorDto(AppConstants.SURVEY_API_ERROR_CODE, message);
			response = new ResponseDto(true, "Problem in calling API", error);
			responseLog.append(message);
		}
		APILoggerDto apiLogger = new APILoggerDto(requestLog.toString(), responseLog.toString());
		apiLoggerService.insert(apiLogger);
		return response;
	}

	private HandoverSurveyReminderRequestDto getReminderRequestDto() {
		HandoverSurveyReminderRequestDto request = new HandoverSurveyReminderRequestDto();

		request.setSendEmail("true");
		request.setSendSMS("true");
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSmsTemplateID(SMS_TEMPLATE_ID);
		return request;
	}
}
