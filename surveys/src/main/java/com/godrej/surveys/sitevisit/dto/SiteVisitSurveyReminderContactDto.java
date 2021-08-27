package com.godrej.surveys.sitevisit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.godrej.surveys.dto.BaseDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteVisitSurveyReminderContactDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4249789830268312976L;
	private String firstName;
	private String lastName;
	private String mobile;
	private String segmentCode;
	@JsonIgnore
	private String transactionDate; // format dd/mm/yyyy
	private String email;
	
	@JsonProperty("field20")
	private String custom20;
	@JsonIgnore
	private String name;
	@JsonIgnore
	private String bookingDate;
	@JsonIgnore
	private String sfid;
	private String reminderNumber;
	
	private String field2;
	private String field4;
	private String field6;
	private String field8;
	private String field9;
	private String field11;
	private String field13;
	private String field14;
	private String field16;
	private String field3;
	private String field10;
	private String field18;

	
	private String surveyType;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSegmentCode() {
		return segmentCode;
	}
	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCustom20() {
		return custom20;
	}
	public void setCustom20(String custom20) {
		this.custom20 = custom20;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getReminderNumber() {
		return reminderNumber;
	}
	public void setReminderNumber(String reminderNumber) {
		this.reminderNumber = reminderNumber;
	}
	
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}
	public String getField6() {
		return field6;
	}
	public void setField6(String field6) {
		this.field6 = field6;
	}
	public String getField8() {
		return field8;
	}
	public void setField8(String field8) {
		this.field8 = field8;
	}
	public String getField9() {
		return field9;
	}
	public void setField9(String field9) {
		this.field9 = field9;
	}
	public String getField11() {
		return field11;
	}
	public void setField11(String field11) {
		this.field11 = field11;
	}
	public String getField13() {
		return field13;
	}
	public void setField13(String field13) {
		this.field13 = field13;
	}
	public String getField14() {
		return field14;
	}
	public void setField14(String field14) {
		this.field14 = field14;
	}
	public String getField16() {
		return field16;
	}
	public void setField16(String field16) {
		this.field16 = field16;
	}
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public String getField10() {
		return field10;
	}
	public void setField10(String field10) {
		this.field10 = field10;
	}
	public String getField18() {
		return field18;
	}
	public void setField18(String field18) {
		this.field18 = field18;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((surveyType == null) ? 0 : surveyType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SiteVisitSurveyReminderContactDto other = (SiteVisitSurveyReminderContactDto) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (surveyType == null) {
			if (other.surveyType != null)
				return false;
		} else if (!surveyType.equals(other.surveyType))
			return false;
		return true;
	}	
	
	
}
