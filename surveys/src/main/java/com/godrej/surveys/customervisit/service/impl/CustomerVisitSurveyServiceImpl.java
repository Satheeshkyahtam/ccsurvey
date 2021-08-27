package com.godrej.surveys.customervisit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godrej.surveys.contactlog.dao.ContactLogDao;
import com.godrej.surveys.customervisit.dao.CustomerVisitSurveyRequestDao;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyContactDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderContactDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderResponseDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyReminderResponseWrapperDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyResponseDto;
import com.godrej.surveys.customervisit.dto.CustomerVisitSurveyResponseWrapperDto;
import com.godrej.surveys.customervisit.service.CustomerVisitSurveyAPIService;
import com.godrej.surveys.customervisit.service.CustomerVisitSurveyService;
import com.godrej.surveys.dao.ProjectDao;
import com.godrej.surveys.dto.BookingParam;
import com.godrej.surveys.dto.ErrorDto;
import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.service.ProjectService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.CommonUtil;
import com.godrej.surveys.util.DateUtil;

@Service
public class CustomerVisitSurveyServiceImpl implements CustomerVisitSurveyService {

	@Autowired
	private CustomerVisitSurveyRequestDao surveyRequestDao;

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerVisitSurveyAPIService rmSurveyAPI;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ContactLogDao contactLogDao;

	@Override
	public List<CustomerVisitSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate) {

		ProjectDto project = projectService.getProject(projectSfid);
		if (project == null) {
			return new ArrayList<>();
		}
		try {
			String preSurveyId= "6126647";

			project.setFromDate(fromDate);
			project.setToDate(toDate);
			project.setViewOnly("Y");
			String transactionDate = dateUtil.getCurrentDate("dd/MM/yyyy");
			project.setTransactionDate(transactionDate);
			project.setSurveyId(preSurveyId);
			String instanceId = "CV_"+ Calendar.getInstance().getTimeInMillis();
			project.setInstanceId(instanceId);		
			Integer parkedRecords = surveyRequestDao.parkRecords(project);
			Integer repeated = contactLogDao.markDuplicate(project);
			StringBuilder countLog = new StringBuilder();
			countLog.append("Parked Records count - ").append(parkedRecords)
			.append(" Repeated Record count - ").append(repeated);
			log.info(countLog.toString());

			return new ArrayList<>(surveyRequestDao.getContacts(project));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}

	@Override
	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate) {
		ProjectDto project = projectDao.getProject(projectSfid);

		String preSurveyId= "6126647";
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
		project.setViewOnly("N");
		String transactionDate = dateUtil.getCurrentDate("dd/MM/yyyy");
		project.setTransactionDate(transactionDate);
		project.setSurveyId(preSurveyId);
		String instanceId = "CV_"+ Calendar.getInstance().getTimeInMillis();
		project.setInstanceId(instanceId);		
		Integer parkedRecords = surveyRequestDao.parkRecords(project);
		Integer repeated = contactLogDao.markDuplicate(project);
		StringBuilder countLog = new StringBuilder();
		countLog.append("Parked Records count - ").append(parkedRecords)
		.append(" Repeated Record count - ").append(repeated);
		log.info(countLog.toString());

		List<CustomerVisitSurveyContactDto> contacts = new ArrayList<>(surveyRequestDao.getContacts(project));
		ResponseDto response =  processSurvey(contacts, project);
		/*
		 * try { surveyRequestDao.clearExtraUpdate(project); }catch (Exception e) {
		 * log.error(e.getMessage() ,e); }
		 */		
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

	private ResponseDto processSurvey(List<CustomerVisitSurveyContactDto> contacts, ProjectDto project) {
		ResponseDto response = processSurvey(contacts, project.getInstanceId());
		Integer statusUpdateCount = updateStatus(project);
		response.addData("statusUpdateCount", statusUpdateCount);
		return response;

	}

	private ResponseDto processSurvey(List<CustomerVisitSurveyContactDto> contacts, String instanceId) {

		if (CommonUtil.isCollectionEmpty(contacts)) {
			return new ResponseDto(true, "No contact for project ");
		}
		ResponseDto response = new ResponseDto(false, "");
		int pageSize = AppConstants.SURVEY_API_PAGE_SIZE;
		int totalImportCount = 0;
		if (contacts.size() <= pageSize) {
			ResponseDto responseChunck = rmSurveyAPI.post(contacts);
			if(responseChunck !=null && !responseChunck.isHasError()) {
				totalImportCount = getImportCount(responseChunck);
				response.addData("importCount", Integer.valueOf(totalImportCount));
				updateContactLogs(contacts, instanceId);
			}
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
			List<CustomerVisitSurveyContactDto> contactChunck = contacts.subList(startIndex, endIndex);

			ResponseDto responseChunck = rmSurveyAPI.post(contactChunck);
			if(responseChunck !=null && !responseChunck.isHasError()) {
				totalImportCount = totalImportCount + getImportCount(responseChunck);
				response.addData("importCount", Integer.valueOf(totalImportCount));
				updateContactLogs(contactChunck, instanceId);
			}
			startIndex = i * pageSize;
		}
		return response;
	}

	private Integer updateContactLogs(List<CustomerVisitSurveyContactDto> contactChunck, String instanceId) {
		String[] bookings = contactChunck.stream().map(contact -> contact.getName()).collect(Collectors.toList())
				.toArray(new String[0]);
		BookingParam bookingParam = new BookingParam();
		bookingParam.setBookings(bookings);
		bookingParam.setInstanceId(instanceId);
		try {
			contactLogDao.updateSentContacts(bookingParam);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return 0;
	}

	private Integer updateStatus(ProjectDto project) {
		try {
			Integer updatedContacts = contactLogDao.markContactLogs(project);
			log.info(project.getInstanceId() + " Contact logs marked count - " + updatedContacts);
			return surveyRequestDao.updateStatusFromContactLogs(project);
			/*
			 * return surveyRequestDao.updateStatus(project);
			 */
			} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	private int getImportCount(ResponseDto response) {

		if (response == null) {
			return 0;
		}
		CustomerVisitSurveyResponseWrapperDto rmAPIWrappedResponse = (CustomerVisitSurveyResponseWrapperDto) response
				.getData("rmSurveyResponse");

		if (rmAPIWrappedResponse == null) {
			return 0;
		}

		CustomerVisitSurveyResponseDto rmAPIResponse = rmAPIWrappedResponse.getResponse();
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
		Set<CustomerVisitSurveyReminderContactDto> contacts = getContactsForFirstReminder(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 1");
			return new ResponseDto(false, "No Contact for Reminder 1");
		}

		for (CustomerVisitSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("1");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "First Reminders Sent");
	}

	public ResponseDto sendSecondReminder(ProjectDto project) {

		Set<CustomerVisitSurveyReminderContactDto> contacts = getContactsForSecondReminder(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 2");
			return new ResponseDto(false, "No Contact for Reminder 2");
		}

		for (CustomerVisitSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("2");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "Second Reminders Sent");

	}

	private void processReminderResponse(ResponseDto response, CustomerVisitSurveyReminderContactDto contact) {
		if(response == null || contact == null || response.isHasError()) {
			return;
		}
		CustomerVisitSurveyReminderResponseWrapperDto wrappedSurveyReminderReponse = (CustomerVisitSurveyReminderResponseWrapperDto) response.getData("rmSurveyReminderResponse");
		if(wrappedSurveyReminderReponse == null) {
			return;
		}
		
		CustomerVisitSurveyReminderResponseDto reminderResponse = wrappedSurveyReminderReponse.getResponse();
		if(reminderResponse == null) {
			return;
		}
		if(CommonUtil.isStringEmpty(reminderResponse.getError())) {
			updateReminderStatus(contact);
		}

	}
	
	private void updateReminderStatus(CustomerVisitSurveyReminderContactDto contact) {
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
	public Set<CustomerVisitSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForFirstReminder(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<CustomerVisitSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForSecondReminder(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}
}
