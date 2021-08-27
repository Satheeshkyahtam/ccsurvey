package com.godrej.surveys.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Sample Rest Controller", tags = {"Sample Rest controller for demo Swagger documentation"})
public class SampleRestController {

	@GetMapping("/sampleRest")
	@ApiOperation(value = "sample rest url")
	public @ResponseBody String sampleRest() {
		
		return "OK";
	}
}
