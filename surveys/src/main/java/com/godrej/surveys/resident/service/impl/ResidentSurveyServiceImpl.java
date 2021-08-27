package com.godrej.surveys.resident.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godrej.surveys.dao.ProjectDao;
import com.godrej.surveys.dto.ErrorDto;
import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.resident.dao.ResidentSurveyRequestDao;
import com.godrej.surveys.resident.dto.ResidentSurveyContactDto;
import com.godrej.surveys.resident.dto.ResidentSurveyReminderContactDto;
import com.godrej.surveys.resident.dto.ResidentSurveyReminderResponseDto;
import com.godrej.surveys.resident.dto.ResidentSurveyReminderResponseWrapperDto;
import com.godrej.surveys.resident.dto.ResidentSurveyResponseDto;
import com.godrej.surveys.resident.dto.ResidentSurveyResponseWrapperDto;
import com.godrej.surveys.resident.service.ResidentSurveyAPIService;
import com.godrej.surveys.resident.service.ResidentSurveyService;
import com.godrej.surveys.service.ProjectService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.CommonUtil;
import com.godrej.surveys.util.DateUtil;

@Service
public class ResidentSurveyServiceImpl implements ResidentSurveyService {

	@Autowired
	private ResidentSurveyRequestDao surveyRequestDao;

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResidentSurveyAPIService rmSurveyAPI;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private ProjectService projectService;

	@Override
	public List<ResidentSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate) {

		ProjectDto project = projectService.getProject(projectSfid);
		if (project == null) {
			return new ArrayList<>();
		}
		try {
			return new ArrayList<>(surveyRequestDao.getContacts(project));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}

	@Override
	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate) {
		ProjectDto project = projectDao.getProject(projectSfid);

		String preSurveyId= "6356252";
		if (project == null) {

			StringBuilder errorMsg = new StringBuilder("No project found for sfid - ");
			errorMsg.append(projectSfid);
			if(log.isInfoEnabled()) {
				log.error(errorMsg.toString());
			}
			return new ResponseDto(true, "No project found for sfid- " + projectSfid);
		}
		project.setFromDate(fromDate);
		project.setToDate(toDate);

		String transactionDate = dateUtil.getCurrentDate("dd/MM/yyyy");
		project.setTransactionDate(transactionDate);
		project.setSurveyId(preSurveyId);

		List<ResidentSurveyContactDto> contacts = new ArrayList<>(surveyRequestDao.getContacts(project));
		ResponseDto response =  processSurvey(contacts, project);
		
		try {
			surveyRequestDao.clearExtraUpdate(project);
		}catch (Exception e) {
			log.error(e.getMessage() ,e);
		}
		return response;
	}
	
	@SuppressWarnings("unused")
	private ResponseDto getFinalResponse(ResponseDto responsePre, ResponseDto responsePost) {
		ResponseDto response = new ResponseDto(false, "");
		boolean hasError =false;
		Integer count =0;
		List<ErrorDto> errors =  new ArrayList<>();
		if(responsePre == null && responsePost ==null) {
			return new ResponseDto(true, "No record to process");
		}
		if(responsePre !=null) {
			hasError = responsePre.isHasError();
			Integer updateCount =  (Integer)(responsePre.getData("importCount")==null?0:responsePre.getData("importCount"));
			count = count +updateCount;
			errors.addAll(responsePre.getErrors());
		}
		if(responsePost !=null) {
			hasError = !hasError?hasError:responsePost.isHasError();
			Integer updateCount =  (Integer)(responsePost.getData("importCount")==null?0:responsePost.getData("importCount"));
			count = count +updateCount;
			errors.addAll(responsePost.getErrors());
		}
		response.setHasError(hasError);
		response.addData("statusUpdateCount", count);
		response.addData("importCount", count);
		response.setErrors(errors);
		return response;
	}

	private ResponseDto processSurvey(List<ResidentSurveyContactDto> contacts, ProjectDto project) {
		ResponseDto response = processSurvey(contacts);
		Integer statusUpdateCount = updateStatus(project);
		response.addData("statusUpdateCount", statusUpdateCount);
		return response;

	}

	private ResponseDto processSurvey(List<ResidentSurveyContactDto> contacts) {

		if (CommonUtil.isCollectionEmpty(contacts)) {
			return new ResponseDto(true, "No contact for project ");
		}
		ResponseDto response = new ResponseDto(false, "");
		int pageSize = AppConstants.SURVEY_API_PAGE_SIZE;
		int totalImportCount = 0;
		if (contacts.size() <= pageSize) {
			ResponseDto responseChunck = rmSurveyAPI.post(contacts);
			totalImportCount = getImportCount(responseChunck);
			response.addData("importCount", Integer.valueOf(totalImportCount));
			return response;
		}

		int totalContacts = contacts.size();
		int pages = totalContacts / pageSize;
		int mod = totalContacts % pageSize;
		if (mod > 0) {
			pages = pages + 1;
		}
		int startIndex = 0;
		int endIndex = 0;

		for (int i = 1; i <= pages; i++) {
			if (i == pages) {
				endIndex = ((i - 1) * pageSize + mod);
				log.info("Last Page");
			} else {
				endIndex = i * pageSize;
			}
			List<ResidentSurveyContactDto> contactChunck = contacts.subList(startIndex, endIndex);

			ResponseDto responseChunck = rmSurveyAPI.post(contactChunck);
			totalImportCount = totalImportCount + getImportCount(responseChunck);
			response.addData("importCount", Integer.valueOf(totalImportCount));
			startIndex = i * pageSize;
		}
		return response;
	}

	private Integer updateStatus(ProjectDto project) {
		try {
			return surveyRequestDao.updateStatus(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return 0;
	}

	private int getImportCount(ResponseDto response) {

		if (response == null) {
			return 0;
		}
		ResidentSurveyResponseWrapperDto rmAPIWrappedResponse = (ResidentSurveyResponseWrapperDto) response
				.getData("rmSurveyResponse");

		if (rmAPIWrappedResponse == null) {
			return 0;
		}

		ResidentSurveyResponseDto rmAPIResponse = rmAPIWrappedResponse.getResponse();
		if (rmAPIResponse == null) {
			return 0;
		}
		return rmAPIResponse.getImportCount();
	}

	@Override
	public ResponseDto sendReminder(ProjectDto project) {
		sendFirstReminder(project);
		sendSecondReminder(project);

		return new ResponseDto(false, "Reminders sent");
	}

	public ResponseDto sendFirstReminder(ProjectDto project) {
		Set<ResidentSurveyReminderContactDto> contacts = getContactsForFirstReminder(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 1");
			return new ResponseDto(false, "No Contact for Reminder 1");
		}

		for (ResidentSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("1");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "First Reminders Sent");
	}

	public ResponseDto sendSecondReminder(ProjectDto project) {

		Set<ResidentSurveyReminderContactDto> contacts = getContactsForSecondReminder(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 2");
			return new ResponseDto(false, "No Contact for Reminder 2");
		}

		for (ResidentSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("2");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "Second Reminders Sent");

	}

	private void processReminderResponse(ResponseDto response, ResidentSurveyReminderContactDto contact) {
		if(response == null || contact == null) {
			return;
		}
		ResidentSurveyReminderResponseWrapperDto wrappedSurveyReminderReponse = (ResidentSurveyReminderResponseWrapperDto) response.getData("rmSurveyReminderResponse");
		if(wrappedSurveyReminderReponse == null) {
			return;
		}
		
		ResidentSurveyReminderResponseDto reminderResponse = wrappedSurveyReminderReponse.getResponse();
		if(reminderResponse == null) {
			return;
		}
		if(CommonUtil.isStringEmpty(reminderResponse.getError())) {
			updateReminderStatus(contact);
		}

	}
	
	private void updateReminderStatus(ResidentSurveyReminderContactDto contact) {
		if(contact == null) {
			return;
		}
		if("1".equals(contact.getReminderNumber())){
			surveyRequestDao.updateFistReminderStaus(contact);
		}
		if("2".equals(contact.getReminderNumber())) {
			surveyRequestDao.updateSecondReminderStaus(contact);
		}
	}

	@Override
	public void sendReminderToAllRegions() {
		List<String> regions = projectService.getRegions();
		sendReminderRegionWise(regions);
	}

	@Override
	public void sendReminderRegionWise(List<String> regions) {
		if (CommonUtil.isCollectionEmpty(regions)) {
			log.info("No Region, can not process Reminder");
		}
		for (String region : regions) {
			sendReminderToRegion(region);
		}
	}

	private void sendReminderToRegion(String region) {
		if (CommonUtil.isStringEmpty(region)) {
			log.info("No region in input");
			return;
		}

		List<ProjectDto> projects = projectService.getProjects(region);

		if (CommonUtil.isCollectionEmpty(projects)) {
			log.info("No project for region -" + region);
			return;
		}

		for (ProjectDto project : projects) {
			sendReminder(project);
		}
	}

	@Override
	public Set<ResidentSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForFirstReminder(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<ResidentSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForSecondReminder(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}
}
