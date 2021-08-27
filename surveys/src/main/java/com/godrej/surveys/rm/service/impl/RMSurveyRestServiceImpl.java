package com.godrej.surveys.rm.service.impl;

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
import com.godrej.surveys.rm.dto.RMSurveyContactDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderContactDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderRequestDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderResponseWrapperDto;
import com.godrej.surveys.rm.dto.RMSurveyRequestDto;
import com.godrej.surveys.rm.dto.RMSurveyResponseWrapperDto;
import com.godrej.surveys.rm.service.RMSurveyAPIService;
import com.godrej.surveys.service.APILoggerService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.DateUtil;

@Service
public class RMSurveyRestServiceImpl implements RMSurveyAPIService {

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
	private static final String SURVEY_ID = "6754624";
	private static final String SMS_TEMPLATE_ID = "441";
	private static final String INVITATION_TEMPLATE_ID = "638353";

	@Override
	public ResponseDto post(List<RMSurveyContactDto> contacts) {

		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");
		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		RMSurveyRequestDto request = getRequestDto();
		request.setContacts(contacts);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {

			String apiResponse = restTemplate.postForObject(url, request, String.class);
			RMSurveyResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					RMSurveyResponseWrapperDto.class);
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

	private RMSurveyRequestDto getRequestDto() {
		RMSurveyRequestDto request = new RMSurveyRequestDto();
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

		Logger log = LoggerFactory.getLogger(RMSurveyRestServiceImpl.class);

		RMSurveyRequestDto request = new RMSurveyRequestDto();
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSendEmail("true");
		request.setSendSMS("true");
		request.setSmsTemplateID(SMS_TEMPLATE_ID);

		RMSurveyContactDto contact = new RMSurveyContactDto();
		contact.setEmail("vivekbirdi@gmail.com");
		contact.setMobile("917738915689");
		contact.setFirstName("Vivek");
		contact.setLastName("Birdi");
		contact.setSegmentCode("S90");
		contact.setTransactionDate("12/12/2019");

		List<RMSurveyContactDto> contacts = new ArrayList<>();
		contacts.add(contact);
		request.setContacts(contacts);

		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		String response = new RestTemplate().postForObject(url, request, String.class);
		log.info(response);
	}

	@Override
	public ResponseDto postReminder(RMSurveyReminderContactDto contact) {
		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");

		String url = SEND_REMINDER_URL + AppConstants.API_KEY;
		RMSurveyReminderRequestDto request = getReminderRequestDto();
		request.setContact(contact);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {
			String apiResponse = restTemplate.postForObject(url, request, String.class);
			RMSurveyReminderResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					RMSurveyReminderResponseWrapperDto.class);
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

	private RMSurveyReminderRequestDto getReminderRequestDto() {
		RMSurveyReminderRequestDto request = new RMSurveyReminderRequestDto();
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSendEmail("true");
		request.setSendSMS("true");
		return request;
	}
}
