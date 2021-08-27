package com.godrej.surveys.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.godrej.surveys.dto.Errors;
import com.godrej.surveys.dto.LoginUserDto;
import com.godrej.surveys.dto.ResponseDto;
import com.godrej.surveys.service.LoginService;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.validator.LoginUserValidator;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;

	@Autowired
	private LoginUserValidator loginUserValidator;
	
	@GetMapping(value = {"/login","/"})
	public ModelAndView openLoginPage(HttpServletRequest request) {
		ModelAndView view  = new ModelAndView();
		view.setViewName("pu/login/login");
		return view;
	}
	
	@PostMapping(value = "/authenticate")
	public @ResponseBody ResponseDto loginuser(LoginUserDto dto) {
		ResponseDto response = null;
		if(dto.getEmail().equals("prakash.idnani@godrejproperties.com") || dto.getEmail().equals("sparikh@godrejproperties.com") || dto.getEmail().equals("rajesh.gupta@godrejproperties.com") 
				|| dto.getEmail().equals("prashant.jain@godrejinds.com") || dto.getEmail().equals("ve.swaminathan@godrejinds.com") 
				|| dto.getEmail().equals("sathish.kyatham@godrejinds.com") || dto.getEmail().equals("vineet.bhardwaj@godrejproperties.com") || dto.getEmail().equals("sachin.suryavanshi@godrejproperties.com"))
		{
			
			if(AppConstants.TEST_ENV) {
				response =  new ResponseDto(false, "Login Successfull");
				response.addData("principal", dto);
				return response;
						
			}
			LoginUserDto loginDto=loginService.getldapUserData(dto);
			Errors errors =  new Errors();
			loginUserValidator.validate(dto, errors);
			if(errors.getErrorCount()>0) {
				response =  new ResponseDto(true, "Error in login");
				response.setErrors(errors.getErrorList());
				return response;
			}
			if(!loginDto.isValid()){//if(loginDto==null)
			 return new ResponseDto(true, "Error in login, Please check username and password");
			}
			response =  new ResponseDto(false, "Login Successfull");
			response.addData("principal", loginDto);
		}
		else
		{
			return new ResponseDto(true, "You are not a authorized to login this portal");
		}
		
		return response;
	}
}
