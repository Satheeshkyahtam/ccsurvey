package com.godrej.surveys.dto;

import java.util.Arrays;

public class BookingParam {

	private String []bookings;
	private String instanceId;
	
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
	@Override
	public String toString() {
		return "BaselineBookingParam [bookings=" + Arrays.toString(bookings) + "]";
	}
}
