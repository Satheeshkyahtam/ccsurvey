package com.godrej.surveys.onboarding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * Contact details of RM Survey
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnboardingSurveyContactDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2165045093833715656L;
	private String firstName;
	private String lastName;
	private String app_id;
	private String user_phone;
	private String segmentCode;
	private String transactionDate; // format dd/mm/yyyy
	private String user_email;
	private String tag_field20;
	private String name;
	private String bookingDate;
	private String tag_field1;
	private String tag_field15;
	private String sentDate;
	private boolean sentStatus;
	
	private String tag_field2;
	private String tag_field4;
	private String tag_field6;
	private String tag_field8;
	private String tag_field9;
	private String tag_field11;
	private String tag_field13;
	private String tag_field14;
	private String tag_field16;
	private String surveyType;
	private String propertyName;
	private String projectName;
	private String tag_field3;
	private String tag_field10;
	private String tag_field18;
	

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
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
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
	public String getTag_field20() {
		return tag_field20;
	}
	public void setTag_field20(String tag_field20) {
		this.tag_field20 = tag_field20;
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
	public String getTag_field1() {
		return tag_field1;
	}
	public void setTag_field1(String tag_field1) {
		this.tag_field1 = tag_field1;
	}
	public String getTag_field15() {
		return tag_field15;
	}
	public void setTag_field15(String tag_field15) {
		this.tag_field15 = tag_field15;
	}
	public String getSentDate() {
		return sentDate;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	public boolean isSentStatus() {
		return sentStatus;
	}
	public void setSentStatus(boolean sentStatus) {
		this.sentStatus = sentStatus;
	}
	public String getTag_field2() {
		return tag_field2;
	}
	public void setTag_field2(String tag_field2) {
		this.tag_field2 = tag_field2;
	}
	public String getTag_field4() {
		return tag_field4;
	}
	public void setTag_field4(String tag_field4) {
		this.tag_field4 = tag_field4;
	}
	public String getTag_field6() {
		return tag_field6;
	}
	public void setTag_field6(String tag_field6) {
		this.tag_field6 = tag_field6;
	}
	public String getTag_field8() {
		return tag_field8;
	}
	public void setTag_field8(String tag_field8) {
		this.tag_field8 = tag_field8;
	}
	public String getTag_field9() {
		return tag_field9;
	}
	public void setTag_field9(String tag_field9) {
		this.tag_field9 = tag_field9;
	}
	public String getTag_field11() {
		return tag_field11;
	}
	public void setTag_field11(String tag_field11) {
		this.tag_field11 = tag_field11;
	}
	public String getTag_field13() {
		return tag_field13;
	}
	public void setTag_field13(String tag_field13) {
		this.tag_field13 = tag_field13;
	}
	public String getTag_field14() {
		return tag_field14;
	}
	public void setTag_field14(String tag_field14) {
		this.tag_field14 = tag_field14;
	}
	public String getTag_field16() {
		return tag_field16;
	}
	public void setTag_field16(String tag_field16) {
		this.tag_field16 = tag_field16;
	}
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTag_field3() {
		return tag_field3;
	}
	public void setTag_field3(String tag_field3) {
		this.tag_field3 = tag_field3;
	}
	public String getTag_field10() {
		return tag_field10;
	}
	public void setTag_field10(String tag_field10) {
		this.tag_field10 = tag_field10;
	}
	public String getTag_field18() {
		return tag_field18;
	}
	public void setTag_field18(String tag_field18) {
		this.tag_field18 = tag_field18;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user_email == null) ? 0 : user_email.hashCode());
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
		OnboardingSurveyContactDto other = (OnboardingSurveyContactDto) obj;
		if (user_email == null) {
			if (other.user_email != null)
				return false;
		} else if (!user_email.equals(other.user_email))
			return false;
		return true;
	}
	
}
