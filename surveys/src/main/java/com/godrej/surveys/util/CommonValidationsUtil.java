package com.godrej.surveys.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vivek Birdi
 *
 */
public class CommonValidationsUtil {
		
	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	//matches 10-digit numbers only
	private static final String MOBILE_NO_REGEX = "^[0-9]{10}$";
	private static final String PAN_NO_REGEX = "[A-Za-z]{5}\\d{4}[A-Za-z]{1}";
	private static Pattern pattern;
	private static Matcher matcher;
	
	/**
	 * @param String 
	 * @return return true if email id is valid
	 */
	public static boolean isValidEmail(String value){
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	/**
	 * @param String 
	 * @return return true if Mobile No is valid
	 */
	public static boolean isValidMobileNO(String value){
		pattern = Pattern.compile(MOBILE_NO_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	/**
	 * @param String 
	 * @return return true if PAN No is valid
	 */
	public static boolean isValidPanNO(String value){
		pattern = Pattern.compile(PAN_NO_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(value);
		return matcher.matches();
	}

}
