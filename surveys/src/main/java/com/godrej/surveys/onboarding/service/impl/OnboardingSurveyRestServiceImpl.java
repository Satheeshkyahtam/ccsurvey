package com.godrej.surveys.onboarding.service.impl;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godrej.surveys.dto.APILoggerDto;
import com.godrej.surveys.dto.ErrorDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyContactDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyReminderContactDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyReminderRequestDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyReminderResponseWrapperDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyRequestDto;
import com.godrej.surveys.onboarding.dto.OnboardingSurveyResponseWrapperDto;
import com.godrej.surveys.onboarding.service.OnboardingSurveyAPIService;
import com.godrej.surveys.service.APILoggerService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.DateUtil;

@Service
public class OnboardingSurveyRestServiceImpl implements OnboardingSurveyAPIService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private APILoggerService apiLoggerService;

	private Logger log = LoggerFactory.getLogger(getClass());
	/*private static final String IMPORT_CONTACT_URL = "https://www.questionpro.com/a/api/questionpro.cx.importTransaction?apiKey=";
	private static final String SEND_REMINDER_URL = "https://www.questionpro.com/a/api/questionpro.cx.sendReminderForEmail?apiKey=";*/
	private static final String IMPORT_CONTACT_URL = "https://app-india.litmusworld.com/rateus/api/feedbackrequests/by_shop_v2";
	private static final String SEND_REMINDER_URL = "";
	
	private static final String SURVEY_ID = "6063132";
	private static final String SMS_TEMPLATE_ID = "161";
	private static final String INVITATION_TEMPLATE_ID = "456034";
	
	/*
	 * private static final String POST_POSSESSION_SURVEY_ID="6356252"; private
	 * static final String FIRST_SMS_TEMPLATE_ID = "149"; private static final
	 * String SECOND_SMS_TEMPLATE_ID = "491"; private static final String
	 * POST_INVITATION_TEMPLATE_ID="533978"; private static final String
	 * SECOND_INVITATION_TEMPLATE_ID="736741"; private static final String
	 * SECOND_POST_INVITATION_TEMPLATE_ID="736741";
	 */
	
	@Override
	public ResponseDto post(List<OnboardingSurveyContactDto> contacts) {

		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");
//		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		String url = IMPORT_CONTACT_URL;
		//OnboardingSurveyRequestDto request = getRequestDto();
		//request.setContacts(contacts);
		ResponseDto response = null;
//		String requestJSON = getRequestJSON(request);
		contacts.get(0).setApp_id("givs_touchpoint");
		String requestJSON = getRequestJSON(contacts);
		requestLog.append(requestJSON);
		try {

			 TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
			    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
			    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			    requestFactory.setHttpClient(httpClient);

			    restTemplate = new RestTemplate(requestFactory);
			    
			String apiResponse = restTemplate.postForObject(url, contacts, String.class);
			OnboardingSurveyResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					OnboardingSurveyResponseWrapperDto.class);
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

	private OnboardingSurveyRequestDto getRequestDto() {
		OnboardingSurveyRequestDto request = new OnboardingSurveyRequestDto();
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		/*request.setSendEmail("true");
		request.setSendSMS("true");*/
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

		Logger log = LoggerFactory.getLogger(OnboardingSurveyRestServiceImpl.class);

		OnboardingSurveyRequestDto request = new OnboardingSurveyRequestDto();
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		/*request.setSendEmail("true");
		request.setSendSMS("true");*/
		request.setSmsTemplateID(SMS_TEMPLATE_ID);

		OnboardingSurveyContactDto contact = new OnboardingSurveyContactDto();
		contact.setUser_email("sathishkpst@gmail.com");
		contact.setUser_phone("919987677726");
		/*contact.setFirstName("Vivek");
		contact.setLastName("Birdi");*/
		contact.setName("Vivek Birdi");
		contact.setSegmentCode("S90");
		contact.setTransactionDate("12/12/2019");

		List<OnboardingSurveyContactDto> contacts = new ArrayList<>();
		contacts.add(contact);
		request.setContacts(contacts);

//		String url = IMPORT_CONTACT_URL + AppConstants.API_KEY;
		String url = IMPORT_CONTACT_URL;
		String response = new RestTemplate().postForObject(url, request, String.class);
		log.info(response);
	}

	@Override
	public ResponseDto postReminder(OnboardingSurveyReminderContactDto contact) {
		StringBuilder requestLog = new StringBuilder();
		StringBuilder responseLog = new StringBuilder();
		String requestInstance = dateUtil.getCurrentDateTime();
		requestLog.append(requestInstance).append("__");
		responseLog.append(requestInstance).append("__");

		String url = SEND_REMINDER_URL + AppConstants.API_KEY;
		OnboardingSurveyReminderRequestDto request = getReminderRequestDto();
		request.setContact(contact);
		ResponseDto response = null;
		String requestJSON = getRequestJSON(request);
		requestLog.append(requestJSON);
		try {
			String apiResponse = restTemplate.postForObject(url, request, String.class);
			OnboardingSurveyReminderResponseWrapperDto wrappedResponse = mapper.readValue(apiResponse,
					OnboardingSurveyReminderResponseWrapperDto.class);
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

	private OnboardingSurveyReminderRequestDto getReminderRequestDto() {
		OnboardingSurveyReminderRequestDto request = new OnboardingSurveyReminderRequestDto();

		request.setSendEmail("true");
		request.setSendSMS("true");
		request.setSurveyID(SURVEY_ID);
		request.setInvitationTemplateID(INVITATION_TEMPLATE_ID);
		request.setSmsTemplateID(SMS_TEMPLATE_ID);
		return request;
	}
}
