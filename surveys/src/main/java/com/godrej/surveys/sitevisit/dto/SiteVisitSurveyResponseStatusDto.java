package com.godrej.surveys.sitevisit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godrej.surveys.dto.BaseDto;

/**
 * 
 * @author Vivek Birdi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteVisitSurveyResponseStatusDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2357390663641068072L;
	private String method;
	private String apiKey;
	private String serverUTC;
	private String id;
	private String message;
	private String url;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getServerUTC() {
		return serverUTC;
	}
	public void setServerUTC(String serverUTC) {
		this.serverUTC = serverUTC;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
