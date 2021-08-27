package com.godrej.surveys.rm.service.impl;

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
import com.godrej.surveys.dao.ProjectDao;
import com.godrej.surveys.dto.BookingParam;
import com.godrej.surveys.dto.ProjectDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.rm.dao.SurveyRequestDao;
import com.godrej.surveys.rm.dto.RMSurveyContactDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderContactDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderResponseDto;
import com.godrej.surveys.rm.dto.RMSurveyReminderResponseWrapperDto;
import com.godrej.surveys.rm.dto.RMSurveyResponseDto;
import com.godrej.surveys.rm.dto.RMSurveyResponseWrapperDto;
import com.godrej.surveys.rm.service.RMSurveyAPIService;
import com.godrej.surveys.rm.service.RMSurveyService;
import com.godrej.surveys.service.ProjectService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.CommonUtil;
import com.godrej.surveys.util.DateUtil;

@Service
public class RMSurveyServiceImpl implements RMSurveyService {

	@Autowired
	private SurveyRequestDao surveyRequestDao;

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RMSurveyAPIService rmSurveyAPI;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ContactLogDao contactLogDao;

	@Override
	public List<RMSurveyContactDto> getContacts(String projectSfid) {

		ProjectDto project = projectService.getProject(projectSfid);
		if (project == null) {
			return new ArrayList<>();
		}
		try {
			return new ArrayList<>(surveyRequestDao.getContacts(project));
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new ArrayList<>();
	}

	@Override
	public ResponseDto sendSurvey(String projectSfid) {
		ProjectDto project = projectDao.getProject(projectSfid);
		if (project == null) {
			StringBuilder errorMsg = new StringBuilder("No project found for sfid - ");
			errorMsg.append(projectSfid);
			log.error(errorMsg.toString());
			return new ResponseDto(true, "No project found for sfid- " + projectSfid);
		}
		project.setSurveyId("6754624");

		String transactionDate = dateUtil.getCurrentDate("dd/MM/yyyy");
		project.setTransactionDate(transactionDate);
		
		//Stage Data
		String instanceId = "RMSurvey"+ Calendar.getInstance().getTimeInMillis();
		project.setInstanceId(instanceId);		
		Integer parkedRecords = surveyRequestDao.parkRecords(project);
		Integer repeated = contactLogDao.markDuplicateForRM(project);
		StringBuilder countLog = new StringBuilder();
		countLog.append("Parked Records count - ").append(parkedRecords)
		.append(" Repeated Record count - ").append(repeated);
		log.info(countLog.toString());
		// End
		
		List<RMSurveyContactDto> contacts = new ArrayList<>(surveyRequestDao.getParkedContacts(project));
		return processSurvey(contacts, project);
	}

	private ResponseDto processSurvey(List<RMSurveyContactDto> contacts, ProjectDto project) {
		ResponseDto response = processContacts(contacts, project.getInstanceId());
//		ResponseDto response = processSurvey(contacts, project.getInstanceId());
		Integer statusUpdateCount = updateStatus(project);
		response.addData("statusUpdateCount", statusUpdateCount);
		return response;

	}

	private ResponseDto processContacts(List<RMSurveyContactDto> contacts, String instanceId) {
	
		ResponseDto response = new ResponseDto(false, "");
		int importCount =0;
		if (CommonUtil.isCollectionEmpty(contacts)) {
			return new ResponseDto(true, "No contact for project ");
		}
		
		List<RMSurveyContactDto> processList =  new ArrayList<>();
		RMSurveyContactDto previousContact = null;
		for(RMSurveyContactDto contact : contacts) {
			
			if(previousContact!=null && contact.getEmail().equalsIgnoreCase(previousContact.getEmail())) {
				ResponseDto surveyResponse = processSurvey(processList,instanceId);
				if(surveyResponse != null) {
					importCount = importCount + (Integer)surveyResponse.getData("importCount");
				}
				processList =  new ArrayList<>();
				processList.add(contact);
				previousContact =null;
				continue;
			}
			processList.add(contact);
			previousContact = contact;
		}
		
		ResponseDto surveyResponse = processSurvey(processList,instanceId);
		if(surveyResponse != null) {
			importCount = importCount + (Integer)surveyResponse.getData("importCount");
		}
		response.addData("importCount", importCount);
		return response;
		
	}
	
	private ResponseDto processSurvey(List<RMSurveyContactDto> contacts, String instanceId) {
		ResponseDto response = new ResponseDto(false, "");
		if (CommonUtil.isCollectionEmpty(contacts)) {
			response.setHasError(true);
			response.setMessage("No Contact to send");
			response.addData("importCount", Integer.valueOf(0));
			return response;
		}
		
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
			List<RMSurveyContactDto> contactChunck = contacts.subList(startIndex, endIndex);

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
	
	private Integer updateContactLogs(List<RMSurveyContactDto> contactChunck, String instanceId) {
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
		int updateCount = 0;
		try {
			Integer updatedContacts = contactLogDao.markContactLogs(project);
			log.info(project.getInstanceId() + " Contact logs marked count - " + updatedContacts);
			updateCount = surveyRequestDao.updateStatusFromContactLogs(project);
			/* updateCount = surveyRequestDao.updateStatus(project); */
		} catch (Exception e) {
			log.error("Error", e);
		}

		/*
		 * try { int clearedUpdates = surveyRequestDao.clearExtraUpdate(project);
		 * updateCount = updateCount -clearedUpdates; }catch (Exception e) {
		 * log.error("Error" ,e); }
		 */		
		return updateCount;
	}

	private int getImportCount(ResponseDto response) {

		if (response == null) {
			return 0;
		}
		RMSurveyResponseWrapperDto rmAPIWrappedResponse = (RMSurveyResponseWrapperDto) response
				.getData("rmSurveyResponse");

		if (rmAPIWrappedResponse == null) {
			return 0;
		}

		RMSurveyResponseDto rmAPIResponse = rmAPIWrappedResponse.getResponse();
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
		Set<RMSurveyReminderContactDto> contacts = getContactsForFirstReminder(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 1");
			return new ResponseDto(false, "No Contact for Reminder 1");
		}

		for (RMSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("1");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "First Reminders Sent");
	}

	public ResponseDto sendSecondReminder(ProjectDto project) {

		Set<RMSurveyReminderContactDto> contacts = getContactsForSecondReminder(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 2");
			return new ResponseDto(false, "No Contact for Reminder 1");
		}

		for (RMSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("2");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "Second Reminders Sent");

	}
	
	@Override
	public ResponseDto sendFirstReminderDyno(ProjectDto project) {
		Set<RMSurveyReminderContactDto> contacts = getContactsForFirstReminderDyno(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 1");
			return new ResponseDto(false, "No Contact for Reminder 1");
		}

		for (RMSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("1");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "First Reminders Sent");
	}

	@Override
	public ResponseDto sendSecondReminderDyno(ProjectDto project) {

		Set<RMSurveyReminderContactDto> contacts = getContactsForSecondReminderDyno(project);

		if (CommonUtil.isCollectionEmpty(contacts)) {
			log.info("No Contact for reminder 2");
			return new ResponseDto(false, "No Contact for Reminder 1");
		}

		for (RMSurveyReminderContactDto contact : contacts) {
			contact.setReminderNumber("2");
			ResponseDto response = rmSurveyAPI.postReminder(contact);
			processReminderResponse(response, contact);
		}
		return new ResponseDto(false, "Second Reminders Sent");

	}


	private void processReminderResponse(ResponseDto response, RMSurveyReminderContactDto contact) {
		if(response == null || contact == null || response.isHasError()) {
			return;
		}
		RMSurveyReminderResponseWrapperDto wrappedSurveyReminderReponse = (RMSurveyReminderResponseWrapperDto) response.getData("rmSurveyReminderResponse");
		if(wrappedSurveyReminderReponse == null) {
			return;
		}
		
		RMSurveyReminderResponseDto reminderResponse = wrappedSurveyReminderReponse.getResponse();
		if(reminderResponse == null) {
			return;
		}
		if(CommonUtil.isStringEmpty(reminderResponse.getError())) {
			updateReminderStatus(contact);
		}

	}
	
	private void updateReminderStatus(RMSurveyReminderContactDto contact) {
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
			project.setSurveyId("6754624");
			sendReminder(project);
		}
	}

	@Override
	public Set<RMSurveyReminderContactDto> getContactsForFirstReminder(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForFirstReminder(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<RMSurveyReminderContactDto> getContactsForFirstReminderDyno(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForFirstReminderDyno(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}
	
	@Override
	public Set<RMSurveyReminderContactDto> getContactsForSecondReminder(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForSecondReminder(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}
	
	@Override
	public Set<RMSurveyReminderContactDto> getContactsForSecondReminderDyno(ProjectDto project) {
		try {
			return surveyRequestDao.getContactsForSecondReminderDyno(project);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return new HashSet<>();
	}
}
