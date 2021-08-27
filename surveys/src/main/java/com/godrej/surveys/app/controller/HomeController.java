package com.godrej.surveys.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	private Logger log =  LoggerFactory.getLogger(getClass());
	
	@GetMapping(value = "ping")	
	public @ResponseBody String ping() {
		
		return "OK";
	}
	
	@GetMapping(value = "testError")
	public @ResponseBody String test(@RequestParam ("noError") String noError) {
		
		if(noError !=null) {
			log.debug("DUBUG_LOG_TEST");
		}
		
		if("N".equals(noError)) {
			throw new RuntimeException("Test Exception");
		}
		return "OK";
	}
}
