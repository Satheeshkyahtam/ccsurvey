package com.godrej.surveys.baseline.dto;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVWriter;

public class BaselineSurveyExcelHelper {

	public static ByteArrayInputStream tutorialsToExcel(List<BaselineSurveyContactDto> contacts, String instanceId) throws Exception {
		
		List<String[]> csvData = createCsvDataForSurvey(contacts);

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("D:\\Satheesh\\Projects\\Litmus World\\Baseline\\"+instanceId+".csv"))) {
            writer.writeAll(csvData);
        }
		return null;
		
	}
	private static List<String[]> createCsvDataForSurvey(List<BaselineSurveyContactDto> contacts) {
        String[] header = { "Touchpoint", "app_id", "user_phone", "transactionDate","user_email","cust_name","name","bookingDate"
  			  ,"sentDate","sentStatus","field1","field2","field3","field4","field6","field8","field9","field10","field11",
  			  "field13","field14","field15","field16","field18","field20","surveyType","propertyName","projectName"};
        List<String[]> list = new ArrayList<>();
        list.add(header);
        /*for (BaselineSurveyContactDto contact : contacts) {
        	 list.add(record1);
        }*/
        
        String surveyID = "";
        String touchpointID = "";
        
        Iterator<BaselineSurveyContactDto> it = contacts.iterator();
		while (it.hasNext()) {
			BaselineSurveyContactDto contact = it.next();
			
			if (contact.getSurveyType() != null && !contact.getSurveyType().equals("null") && !contact.getSurveyType().equals("")) {
				if (contact.getSurveyType().equals("PRE_POSSESSION")) {
					surveyID = "6356212";
					touchpointID = "ntp4_touchpoint";
				} else if (contact.getSurveyType().equals("POST_POSSESSION")) {
					surveyID = "6356217";
					touchpointID = "epn8_touchpoint";
				} else {
					surveyID = "";
					touchpointID = "";
				}
			} else {
				surveyID = "";
				touchpointID = "";
			}
			
			list.add(new String[] { "Touchpoint", touchpointID, contact.getMobile(), contact.getTransactionDate(),contact.getEmail(),contact.getFirstName(),contact.getName(),contact.getBookingDate(),contact.getSentDate(),"FALSE"
					,contact.getField1(),contact.getField2(),contact.getField3(),contact.getField4(),contact.getField6(),contact.getField8()
					,contact.getField9(),contact.getField10(),contact.getField11(),contact.getField13(),contact.getField14()
					,contact.getField15(),contact.getField16(),surveyID,contact.getField20(),contact.getSurveyType(),contact.getPropertyName(),contact.getField15()}); 
		}

        return list;
    } 
}