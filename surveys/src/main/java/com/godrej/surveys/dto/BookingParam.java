package com.godrej.surveys.dto;

import java.util.Arrays;

public class BookingParam {

	private String []bookings;
	private String instanceId;
	
	/* Added by A */
	private Boolean apiFlag;
	private String apiRes;
	/* END Added by A */
	
	public String[] getBookings() {
		return bookings;
	}
	public void setBookings(String[] bookings) {
		this.bookings = bookings;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public Boolean getApiFlag() {
		return apiFlag;
	}
	public void setApiFlag(Boolean apiFlag) {
		this.apiFlag = apiFlag;
	}
	public String getApiRes() {
		return apiRes;
	}
	public void setApiRes(String apiRes) {
		this.apiRes = apiRes;
	}
	@Override
	public String toString() {
		return "BaselineBookingParam [bookings=" + Arrays.toString(bookings) + "]";
	}
}
