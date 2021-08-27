package com.godrej.surveys.validator;

import org.springframework.stereotype.Component;

import com.godrej.surveys.dto.Errors;
import com.godrej.surveys.dto.LoginUserDto;
import com.godrej.surveys.util.CommonUtil;

@Component
public class LoginUserValidator implements Validator{

	@Override
	public void validate(Object object, Errors errors) {
		if(object == null){
			errors.addError("invalid.data", "invoice cannot be null!");
			return;
		}
		if(!object.getClass().isAssignableFrom(LoginUserDto.class)){
			errors.addError("invalid.type", "Invalid class type, object must be of type InvoiceDto!");
			return;
		}

		LoginUserDto user = (LoginUserDto) object;
		
		String email = user.getEmail();
		if(CommonUtil.isStringEmpty(email)) {
			errors.addError("invalid.email", "Invalid email address");
		}
		String authToken =  user.getPassword();
		if(CommonUtil.isStringEmpty(authToken)) {
			errors.addError("invalid.password", "Password can not be blank");
		}
	}

}
