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
	
	
	public static final String LW_SFTP_HOST="";
	public static final String LW_SFTP_USERNAME="";
	
	public static final String LW_ONBOARDING_SURVEY_FOLDER_PATH="/home/godrej_properties/litmus/feedbacks/customer_onboarding_survey/incoming";
	public static final String LW_REGISTRATION_SURVEY_FOLDER_PATH="/home/godrej_properties/litmus/feedbacks/registration_survey/incoming";
	public static final String LW_HANDOVER_SURVEY_FOLDER_PATH="/home/godrej_properties/litmus/feedbacks/handover_survey/incoming";
	public static final String LW_BASELINE_SURVEY_FOLDER_PATH="/home/godrej_properties/litmus/feedbacks/half_yearly_survey/incoming";

	
	public static final String LW_APP_ID_ONBOARDING="givs_touchpoint";
	public static final String LW_APP_ID_REGISTRATION="3qwp_touchpoint";
	public static final String LW_APP_ID_HANDOVER="ivhh_touchpoint";
	
	public static final String LW_TOUCHPOINT="Touchpoint";
	
	
	public static final String SFDC_USERNAME = "appadmin@godrejproperties.com";
	public static final String SFDC_PASSWORD= "apiadmin@2019VNfEJzl43L7Mar6a6EHSRBqvs";
	public static final String SFDC_LOGINURL= "https://godrej.my.salesforce.com";
	public static final String SFDC_GRANTSERVICE= "/services/oauth2/token?grant_type=password";
	public static final String SFDC_CLIENTID = "3MVG9Y6d_Btp4xp7lt5FL02Cc.VrY2x6HA5K.VU9tuKI0QMM6fkndLpgKvlJbsP4IM_1M5Pi_7E51res6e6Hd";
	public static final String SFDC_CLIENTSECRET = "2B6529FF8055727D581688C832A68204937E06BF4791D5363C76F5821D8739FB";
	
	public static final String SFDC_UPDATEBASELINESURVEY = "https://godrej.my.salesforce.com/services/apexrest/api/UpdateBaselineSurveyField";
}
