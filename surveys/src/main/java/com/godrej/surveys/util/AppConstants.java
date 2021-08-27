package com.godrej.surveys.util;

import java.time.format.DateTimeFormatter;

/**
 * This class defines list of constants to be used in application.
 * @author Vivek Birdi
 *
 */
public final class AppConstants {
	
	private AppConstants() {
		/* Instantiation not allowed from outside*/
	}

	public static final String LDAP_HOST = "10.21.48.21";
	public static final String LDAP_PORT = "389";
	public static final String LDAP_DN = "selfservice.portal@godrejproperties.com";
	public static final String LDAP_PW = "DFER$#34";
	public static final String BASE_DN = "DC=godrejinds,DC=com";
	public static final String LDAP_AUTH_METHOD = "simple";
	public static final String LDAP_VERSION = "3";
	public static final String API_KEY="98209359-ef6c-47c1-89e7-359596245ed9";
	public static final String SURVEY_API_ERROR_CODE="ERS501";
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT); 
	public static final boolean TEST_ENV=false;
	public static final int SURVEY_API_PAGE_SIZE=500;

}
