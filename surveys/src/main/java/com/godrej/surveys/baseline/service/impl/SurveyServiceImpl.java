package com.godrej.surveys.baseline.service.impl;

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

import com.godrej.surveys.baseline.dao.BaselineSurveyRequestDao;
import com.godrej.surveys.baseline.dto.BaselineBookingParam;
import com.godrej.surveys.baseline.dto.BaselineSurveyContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderContactDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderResponseDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyReminderResponseWrapperDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyResponseDto;
import com.godrej.surveys.baseline.dto.BaselineSurveyResponseWrapperDto;
import com.godrej.surveys.baseline.service.BaselineSurveyAPIService;
import com.godrej.surveys.baseline.service.BaselineSurveyService;
import com.godrej.surveys.contactlog.dao.ContactLogDao;
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
public class SurveyServiceImpl implements BaselineSurveyService {

	@Autowired
	private BaselineSurveyRequestDao surveyRequestDao;

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaselineSurveyAPIService rmSurveyAPI;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ContactLogDao contactLogDao;

	@Override
	public List<BaselineSurveyContactDto> getContacts(String projectSfid, String fromDate, String toDate) {

		ProjectDto project = projectService.getProject(projectSfid);
		if (project == null) {
			return new ArrayList<>();
		}
		project.setFromDate(fromDate);
		project.setToDate(toDate);
		try {
			return new ArrayList<>(surveyRequestDao.getContacts(project));
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new ArrayList<>();
	}

	@Override
//	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseDto sendSurvey(String projectSfid, String fromDate, String toDate) {
		ProjectDto project = projectDao.getProject(projectSfid);

		String preSurveyId= "6356212";
		String postSurveyId= "6356217";
		if (project == null) {

			StringBuilder errorMsg = new StringBuilder("No project found for sfid - ");
			errorMsg.append(projectSfid);
			log.error(errorMsg.toString());
			return new ResponseDto(true, "No project found for sfid- " + projectSfid);
		}
		project.setFromDate(fromDate);
		project.setToDate(toDate);

		String transactionDate = dateUtil.getCurrentDate("dd/MM/yyyy");
		project.setTransactionDate(transactionDate);
		project.setSurveyId(preSurveyId);
		
		project.setSurveyId(preSurveyId);
		String instanceId = "Baseline_"+ Calendar.getInstance().getTimeInMillis();
		project.setInstanceId(instanceId);		
		Integer parkedRecords = surveyRequestDao.parkRecords(project);
		Integer repeated = contactLogDao.markDuplicate(project);
		StringBuilder countLog = new StringBuilder();
		countLog.append("Parked Records count - ").append(parkedRecords)
		.append(" Repeated Record count - ").append(repeated);
		log.info(countLog.toString());


		List<BaselineSurveyContactDto> contacts = new ArrayList<>(surveyRequestDao.getPrePossessionParkedContacts(project));
		ResponseDto responsePre =  processSurvey(contacts, project, "PRE");
		
		project.setSurveyId(postSurveyId);
		contacts = new ArrayList<>(surveyRequestDao.getPostPossessionParkedContacts(project));
		ResponseDto responsePost =  processSurvey(contacts, project, "POST");
		
		/*
		 * try { surveyRequestDao.clearExtraUpdate(project); StringBuilder info = new
		 * StringBuilder(); info.append("-- Cleared data for project - ")
		 * .append(projectSfid) .append("  From date - " ).append(fromDate)
		 * .append(" To date - ").append(toDate); log.info(info.toString());
		 * 
		 * }catch (Exception e) { log.error("Error" ,e); }
		 */
		return getFinalResponse(responsePre, responsePost);
	}
	
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

	private ResponseDto processSurvey(List<BaselineSurveyContactDto> contacts, ProjectDto project,String type) {
		ResponseDto response = new ResponseDto(); 
		processSurvey(contacts, type, project.getInstanceId());
		Integer statusUpdateCount = updateStatus(project,type);
		
		/*
		 * StringBuilder info = new StringBuilder();
		 * info.append(project.getSfid()).append(" FROM Date- ")
		 * .append(project.getFromDate()) .append(" To Date - ")
		 * .append(project.getToDate())
		 * .append(" Contacts - ").append(contacts.toString());
		 * log.info(info.toString()); Integer statusUpdateCount =
		 * updateStatus(contacts);
		 */
		response.addData("statusUpdateCount", statusUpdateCount);
		return response;

	}

	private ResponseDto processSurvey(List<BaselineSurveyContactDto> contacts,String type, String instanceId) {

		if (CommonUtil.isCollectionEmpty(contacts)) {
			return new ResponseDto(true, "No contact for project ");
		}
		ResponseDto response = new ResponseDto(false, "");
		int pageSize = AppConstants.SURVEY_API_PAGE_SIZE;
		int totalImportCount = 0;
		if (contacts.size() <= pageSize) {
			ResponseDto responseChunck = rmSurveyAPI.post(contacts,type);
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
			List<BaselineSurveyContactDto> contactChunck = contacts.subList(startIndex, endIndex);

			ResponseDto responseChunck = rmSurveyAPI.post(contactChunck,type);
			if(responseChunck !=null && !responseChunck.isHasError()) {
				totalImportCount = totalImportCount + getImportCount(responseChunck);
				response.addData("importCount", Integer.valueOf(totalImportCount));
				updateContactLogs(contactChunck, instanceId);
			}
			startIndex = i * pageSize;
		}
		return response;
	}

	private Integer updateContactLogs(List<BaselineSurveyContactDto> contactChunck, String instanceId) {
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

	
	
	private Integer updateStatus(List<BaselineSurveyContactDto> contacts) {
		
		
		try {
			String[] bookings = contacts.stream().map(contact -> contact.getName()).collect(Collectors.toList())
					.toArray(new String[0]);
			BaselineBookingParam bookingParam = new BaselineBookingParam();
			bookingParam.setBookings(bookings);
			return surveyRequestDao.updateStatusBookings(bookingParam);
		} catch (Exception e) {
			log.error("Error", e);
		}

		return 0;
	} 
	
	private Integer updateStatus(ProjectDto project, String type) {
		try {
			Integer updatedContacts = contactLogDao.markContactLogs(project);
			log.info(project.getInstanceId() + " Contact logs marked count - " + updatedContacts);
			return surveyRequestDao.updateStatusFromContactLogs(project);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
		/*
		 * return 0; try { if("PRE".equals(type)) { return
		 * surveyRequestDao.updateStatus(project); } return
		 * surveyRequestDao.updateStatusPost(project); } catch (Exception e) {
		 * log.error("Error", e); }
		 * 
		 * return 0;
		 */
	}

	private int getImportCount(ResponseDto response) {

		if (response == null) {
			return 0;
		}
		BaselineSurveyResponseWrapperDto rmAPIWrappedResponse = (BaselineSurveyResponseWrapperDto) response
				.getData("rmSurveyResponse");

		if (rmAPIWrappedResponse == null) {
			return 0;
		}

		BaselineSurveyResponseDto rmAPIResponse = rmAPIWrappedResponse.getResponse();
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
		Set<BaselineSurveyReminderContactDto> contacts = getContactsForFirstReminder(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 1");
			return new ResponseDto(false, "No Contact for Reminder 1");
		}

		for (BaselineSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("1");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "First Reminders Sent");
	}

	public ResponseDto sendSecondReminder(ProjectDto project) {

		Set<BaselineSurveyReminderContactDto> contacts = getContactsForSecondReminder(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 2");
			return new ResponseDto(false, "No Contact for Reminder 2");
		}

		for (BaselineSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("2");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "Second Reminders Sent");

	}

	private void processReminderResponse(ResponseDto response, BaselineSurveyReminderContactDto contact) {
		if(response == null || contact == null || response.isHasError()) {
			return;
		}
		BaselineSurveyReminderResponseWrapperDto wrappedSurveyReminderReponse = (BaselineSurveyReminderResponseWrapperDto) response.getData("rmSurveyReminderResponse");
		if(wrappedSurveyReminderReponse == null) {
			return;
		}
		
		BaselineSurveyReminderResponseDto reminderResponse = wrappedSurveyReminderReponse.getResponse();
		if(reminderResponse == null) {
			return;
		}
		if(CommonUtil.isStringEmpty(reminderResponse.getError())) {
			updateReminderStatus(contact);
		}

	}
	
	private void updateReminderStatus(BaselineSurveyReminderContactDto contact) {
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
	public Set<BaselineSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForFirstReminder(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<BaselineSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForSecondReminder(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}
}
