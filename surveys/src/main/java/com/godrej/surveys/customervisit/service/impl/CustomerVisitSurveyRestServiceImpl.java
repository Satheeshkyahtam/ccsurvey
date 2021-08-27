package com.godrej.surveys.customervisit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyContactDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderContactDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderRequestDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderResponseWrapperDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyRequestDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyResponseWrapperDto;
import com.godrej.surveys.customervisit.service.CustomerVisitSurveyAPIService;
import com.godrej.surveys.dto.APILoggerDto;
import com.godrej.surveys.dto.ErrorDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.service.APILoggerService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.DateUtil;

@Service
public class CustomerVisitSurveyRestServiceImpl implements CustomerVisitSurveyAPIService {

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
	private static final String SURVEY_ID = "6126647";
	private static final String INVITATION_TEMPLATE_ID = "470282";
	private static final String SMS_TEMPLATE_ID = "167";
	
	/*
	 * private static final String POST_POSSESSION_SURVEY_ID="6356252"; private
	 * static final String FIRST_SMS_TEMPLATE_ID = "149"; private static final
	 * String SECOND_SMS_TEMPLATE_ID = "491"; private static final String
	 * POST_INVITATION_TEMPLATE_ID="533978"; private static final String
	 * SECOND_INVITATION_TEMPLATE_ID="736741"; private static final String
	 * SECOND_POST_INVITATION_TEMPLATE_ID="736741";
	 */
	
	@Override
	public ResponseDto post(List<CustomerVisitSurveyContactDto> contacts) {

		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");
		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		CustomerVisitSurveyRequestDto request = getRequestDto();
		request.setContacts(contacts);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {

			String apiResponse = restTemplate.postForObject(url, request, String.class);
			CustomerVisitSurveyResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					CustomerVisitSurveyResponseWrapperDto.class);
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

	private CustomerVisitSurveyRequestDto getRequestDto() {
		CustomerVisitSurveyRequestDto request = new CustomerVisitSurveyRequestDto();
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

		Logger log = LoggerFactory.getLogger(CustomerVisitSurveyRestServiceImpl.class);

		CustomerVisitSurveyRequestDto request = new CustomerVisitSurveyRequestDto();
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSendEmail("true");
		request.setSendSMS("true");
		request.setSmsTemplateID(SMS_TEMPLATE_ID);

		CustomerVisitSurveyContactDto contact = new CustomerVisitSurveyContactDto();
		contact.setEmail("vivekbirdi@gmail.com");
		contact.setMobile("917738915689");
		contact.setFirstName("Vivek");
		contact.setLastName("Birdi");
		contact.setSegmentCode("S90");
		contact.setTransactionDate("12/12/2019");

		List<CustomerVisitSurveyContactDto> contacts = new ArrayList<>();
		contacts.add(contact);
		request.setContacts(contacts);

		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		String response = new RestTemplate().postForObject(url, request, String.class);
		log.info(response);
	}

	@Override
	public ResponseDto postReminder(CustomerVisitSurveyReminderContactDto contact) {
		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");

		String url = SEND_REMINDER_URL + AppConstants.API_KEY;
		CustomerVisitSurveyReminderRequestDto request = getReminderRequestDto();
		request.setContact(contact);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {
			/*
			 * String apiResponse =
			 * "{\"response\":{\"status\":\"Reminder sent successfully.\"},\"status\":{\"method\":\"questionpro.cx.sendReminderForEmail\",\"apiKey\":\"98209359-ef6c-47c1-89e7-359596245ed9\",\"serverUTC\":1612481884909,\"id\":200,\"message\":\"OK\",\"url\":\"/a/api/questionpro.cx.sendReminderForEmail\"}}"
			 * ;
			 */
			String apiResponse = restTemplate.postForObject(url, request, String.class);
			CustomerVisitSurveyReminderResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					CustomerVisitSurveyReminderResponseWrapperDto.class);
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

	private CustomerVisitSurveyReminderRequestDto getReminderRequestDto() {
		CustomerVisitSurveyReminderRequestDto request = new CustomerVisitSurveyReminderRequestDto();

		request.setSendEmail("true");
		request.setSendSMS("true");
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSmsTemplateID(SMS_TEMPLATE_ID);
		return request;
	}
}
