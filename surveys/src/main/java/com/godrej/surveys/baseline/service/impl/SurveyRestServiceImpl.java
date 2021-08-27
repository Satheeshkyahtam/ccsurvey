package com.godrej.surveys.baseline.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godrej.surveys.baseline.dto.BaselineSurveyContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderRequestDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderResponseWrapperDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyRequestDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyResponseWrapperDto;
import com.godrej.surveys.baseline.service.BaselineSurveyAPIService;
import com.godrej.surveys.dto.APILoggerDto;
import com.godrej.surveys.dto.ErrorDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.service.APILoggerService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.DateUtil;

@Service
public class SurveyRestServiceImpl implements BaselineSurveyAPIService {

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
	private static final String SURVEY_ID = "6356212";
	private static final String POST_POSSESSION_SURVEY_ID="6356217";
	private static final String SMS_TEMPLATE_ID = "149";
	private static final String FIRST_SMS_TEMPLATE_ID = "149";
	private static final String SECOND_SMS_TEMPLATE_ID = "149";
	private static final String INVITATION_TEMPLATE_ID = "533507";
	private static final String POST_INVITATION_TEMPLATE_ID="533978";

	private static final String SECOND_INVITATION_TEMPLATE_ID="533507"; //736741
	private static final String SECOND_POST_INVITATION_TEMPLATE_ID="533978";
	
	@Override
	public ResponseDto post(List<BaselineSurveyContactDto> contacts, String type) {

		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");
		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		BaselineSurveyRequestDto request = getRequestDto(type);
		request.setContacts(contacts);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {

			String apiResponse = restTemplate.postForObject(url, request, String.class);
			BaselineSurveyResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					BaselineSurveyResponseWrapperDto.class);
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

	private BaselineSurveyRequestDto getRequestDto(String type) {
		BaselineSurveyRequestDto request = new BaselineSurveyRequestDto();
		if("PRE".equals(type)) {
			request.setSurveyID(SURVEY_ID);
			request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		}else {
			request.setSurveyID(POST_POSSESSION_SURVEY_ID);
			request.setInvitationTemplateID(POST_INVITATION_TEMPLATE_ID);
		}

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

		Logger log = LoggerFactory.getLogger(SurveyRestServiceImpl.class);

		BaselineSurveyRequestDto request = new BaselineSurveyRequestDto();
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSendEmail("true");
		request.setSendSMS("true");
		request.setSmsTemplateID(SMS_TEMPLATE_ID);

		BaselineSurveyContactDto contact = new BaselineSurveyContactDto();
		contact.setEmail("vivekbirdi@gmail.com");
		contact.setMobile("917738915689");
		contact.setFirstName("Vivek");
		contact.setLastName("Birdi");
		contact.setSegmentCode("S90");
		contact.setTransactionDate("12/12/2019");

		List<BaselineSurveyContactDto> contacts = new ArrayList<>();
		contacts.add(contact);
		request.setContacts(contacts);

		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		String response = new RestTemplate().postForObject(url, request, String.class);
		log.info(response);
	}

	@Override
	public ResponseDto postReminder(BaselineSurveyReminderContactDto contact) {
		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");

		String url = SEND_REMINDER_URL + AppConstants.API_KEY;
		BaselineSurveyReminderRequestDto request = getReminderRequestDto(contact.getReminderNumber(), contact.getSurveyType());
		request.setContact(contact);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {
			String apiResponse = restTemplate.postForObject(url, request, String.class);
			BaselineSurveyReminderResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					BaselineSurveyReminderResponseWrapperDto.class);
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

	private BaselineSurveyReminderRequestDto getReminderRequestDto(String reminderNo, String type) {
		BaselineSurveyReminderRequestDto request = new BaselineSurveyReminderRequestDto();

		request.setSendEmail("true");
		request.setSendSMS("true");

		if("PRE_POSSESSION".equals(type)) {
			request.setSurveyID(SURVEY_ID);
			request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		}else {
			request.setSurveyID(POST_POSSESSION_SURVEY_ID);
			request.setInvitationTemplateID(POST_INVITATION_TEMPLATE_ID);
		}

		if("2".equalsIgnoreCase(reminderNo)) {
			request.setSmsTemplateID(SECOND_SMS_TEMPLATE_ID);
			if("PRE_POSSESSION".equals(type)) {
				request.setInvitationTemplateID(SECOND_INVITATION_TEMPLATE_ID);
			}else {
				request.setInvitationTemplateID(SECOND_POST_INVITATION_TEMPLATE_ID);
			}
		}else {
			request.setSmsTemplateID(FIRST_SMS_TEMPLATE_ID);
		}
		return request;
	}
}
