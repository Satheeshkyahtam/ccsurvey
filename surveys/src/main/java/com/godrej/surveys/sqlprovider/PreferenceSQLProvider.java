package com.godrej.surveys.sqlprovider;

public class PreferenceSQLProvider {
	
	public String getInsertQuery() {
		StringBuilder query =  new StringBuilder();
		query.append("INSERT INTO rmsurvey.g_survey_preference ")
		.append(" (survey , survey_id, email_template_id, sms_template_id, ")
		.append(" FR_etemplate_id , FR_mtemplate_id , SR_etemplate_id, SR_mtemplate_id,")
		.append(" isActive, isFRActive, isSRActive, isEmailActive, isSMSActive,")
		.append(" isFREmailActive, isFRSMSActive, isSREmailActive, isSRSMSActive, ")
		.append(" days_to_fr, days_to_sr, api_url,  ")
		.append(" created, updated ) ")
		.append(" values ( #{survey}, #{surveyId},#{emailTemplateId},")
		.append(" #{smsTemplateId}, #{frEmailTemplateId}, #{frSMSTemplateId}, ")
		.append(" #{srEmailTemplateId}, #{srSMSTemplateId}, #{isActive}, ")
		.append(" #{isFRActive}, #{isSRActive}, ")
		.append(" #{isEmailActive}, #{isSMSActive}, #{isFREmailActive}, ")
		.append(" #{isFRSMSActive}, #{isSREmailActive}, #{isSRSMSActive}, ")
		.append(" #{daysToFR}, #{daysToSR}, #{apiURL}, ")
		.append(" now(), now()) ");
		return query.toString();
	}

	public String getUpdateQuery() {
		StringBuilder query =  new StringBuilder();
		
		query.append("Update rmsurvey.g_survey_preference ")
		.append(" SET survey_id =  #{surveyId} , email_template_id = #{emailTemplateId} , sms_template_id =#{smsTemplateId}, ")
		.append(" FR_etemplate_id =#{frEmailTemplateId} , FR_mtemplate_id = #{frSMSTemplateId} , SR_etemplate_id =#{srEmailTemplateId}, SR_mtemplate_id = #{srSMSTemplateId}, ")
		.append(" isActive = #{isActive}, isFRActive = #{isFRActive}, isSRActive = #{isSRActive} , isEmailActive =#{isEmailActive}, isSMSActive =#{isSMSActive},")
		.append(" isFREmailActive = #{isFREmailActive}, isFRSMSActive = #{isFRSMSActive}, isSREmailActive =#{isSREmailActive}, isSRSMSActive = #{isSRSMSActive}, ")
		.append(" days_to_fr = #{daysToFR}, days_to_sr = #{daysToSR}, api_url =  #{apiURL},  ")
		.append("  updated = now() ")
		.append(" WHERE g_survey_preference_id =  #{surveyPreferenceId} ");
		
		return query.toString();
	}
	
	public String getSelectQuery() {
		StringBuilder query =  new StringBuilder();
		query.append(" Select g_survey_preference_id, ")
		.append(" survey , survey_id, email_template_id, sms_template_id ")
		.append(" FR_etemplate_id , FR_mtemplate_id , SR_etemplate_id, SR_mtemplate_id")
		.append(" isActive, isFRActive, isSRActive, isEmailActive, isSMSActive,")
		.append(" isFREmailActive, isFRSMSActive, isSREmailActive, isSRSMSActive, ")
		.append(" days_to_fr, days_to_sr, api_url, api_key, ")
		.append(" created, updated FROM rmsurvey.g_survey_preference ")
		.append(" WHERE survey = #{survey}");
			
		return query.toString();
	}
}
