package com.godrej.surveys.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	
	@GetMapping("/pr/dashboard")
	public ModelAndView dashboard() {
		
		ModelAndView mView = new ModelAndView();
		mView.setViewName("pro/survey/dashboard");
		return mView;
	}
}
