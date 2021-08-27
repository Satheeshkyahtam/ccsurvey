package com.godrej.surveys.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.godrej.surveys.dto.Errors;
import com.godrej.surveys.util.CommonUtil;
import com.godrej.surveys.util.CommonValidationsUtil;

/**
 * Common Validation Util
 * @author Vivek Birdi
 *
 */
@Component
public class ValidationUtil {
	
	/**
	 * This method check if the string object is empty.
	 * @param field - String value
	 * @param errors - Errors class object.
	 * @param errorCode- Error Code (String value e.g code.invalid)
	 * @param message -  Error message (e.g code is mandatory)
	 */
	public void rejectIfEmpty(String field, Errors errors, String errorCode, String message){

		if(errors == null || errorCode == null || CommonUtil.isStringEmpty(message) ){
			return;
		}

		if(CommonUtil.isStringEmpty(field)){
			errors.addError(errorCode, message);
		}		
	}
	
	/**
	 * General purpose method to reject the validation.
	 - Errors class object.
	 * @param errorCode- Error Code (String value e.g code.invalid)
	 * @param message -  Error message (e.g code is invalid)
	 */
	public void reject(Errors errors, String errorCode, String message){
		if(errors == null || errorCode == null || StringUtils.isEmpty(message) ){
			return;
		}
		errors.addError(errorCode, message);
	}
	
	public void classNotSupported(Errors errors, String errorCode, String message){
			errors.addError(errorCode, message);
	}
	
	public void rejectIfInvalidMail(String field, Errors errors, String errorCode, String message){

		if(errors == null || errorCode == null || CommonUtil.isStringEmpty(message) ){
			return;
		}
		if(!CommonValidationsUtil.isValidEmail(field)){
			errors.addError(errorCode, message);
		}			
	}
	
	public void rejectIfInvalidMobileNo(String field, Errors errors, String errorCode, String message){
		if(errors == null || errorCode == null || CommonUtil.isStringEmpty(message) ){
			return;
		}
		if(!CommonValidationsUtil.isValidMobileNO(field)){
			errors.addError(errorCode, message);
		}		
	}
	public void rejectIfInvalidPANNo(String field, Errors errors, String errorCode, String message){
		if(errors == null || errorCode == null || CommonUtil.isStringEmpty(message) ){
			return;
		}
		if(!CommonValidationsUtil.isValidPanNO(field)){
			errors.addError(errorCode, message);
		}		
	}
	
}
