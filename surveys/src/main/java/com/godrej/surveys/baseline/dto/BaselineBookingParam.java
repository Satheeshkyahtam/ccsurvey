package com.godrej.surveys.baseline.dto;

import java.util.Arrays;

public class BaselineBookingParam {

	private String []bookings;

	public String[] getBookings() {
		return bookings;
	}
	public void setBookings(String[] bookings) {
		this.bookings = bookings;
	}
	@Override
	public String toString() {
		return "BaselineBookingParam [bookings=" + Arrays.toString(bookings) + "]";
	}
	
	
	
}
