package com.godrej.surveys.dto;

import com.godrej.surveys.dto.BaseDto;

public class ProjectDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2008198793985210227L;
	private String sfid;
	private String name;
	private String segmentCode;
	private String transactionDate;
	private String region;
	private String fromDate;
	private String toDate;
	private String surveyId;
	private int intervalP;
	private int intervalP2;
	private String instanceId;
	private String viewOnly = "N";
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSegmentCode() {
		return segmentCode;
	}
	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public int getIntervalP() {
		return intervalP;
	}
	public void setIntervalP(int intervalP) {
		this.intervalP = intervalP;
	}
	public int getIntervalP2() {
		return intervalP2;
	}
	public void setIntervalP2(int intervalP2) {
		this.intervalP2 = intervalP2;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getViewOnly() {
		return viewOnly;
	}
	public void setViewOnly(String viewOnly) {
		this.viewOnly = viewOnly;
	}	
	
}
