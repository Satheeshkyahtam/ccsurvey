package com.godrej.surveys.handover.dto;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVWriter;

public class HandoverSurveyExcelHelper {
	
public static ByteArrayInputStream tutorialsToExcel(List<HandoverSurveyContactDto> contacts, String instanceId) throws Exception {
		
		List<String[]> csvData = createCsvDataForSurvey(contacts);

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("D:\\Satheesh\\Projects\\Litmus World\\Handover\\"+instanceId+".csv"))) {
            writer.writeAll(csvData);
        }
		return null;
		
	}
	private static List<String[]> createCsvDataForSurvey(List<HandoverSurveyContactDto> contacts) {
        String[] header = { "Touchpoint", "app_id", "user_phone", "transactionDate","user_email","cust_name","name","bookingDate"
  			  ,"sentDate","sentStatus","field1","field2","field3","field4","field6","field8","field9","field10","field11",
  			  "field13","field14","field15","field16","field18","field20","surveyType","propertyName","projectName"};
        List<String[]> list = new ArrayList<>();
        list.add(header);
        /*for (OnboardingSurveyContactDto contact : contacts) {
        	 list.add(record1);
        }*/
        Iterator<HandoverSurveyContactDto> it = contacts.iterator();
		while (it.hasNext()) {
			HandoverSurveyContactDto contact = it.next();
			list.add(new String[] { "Touchpoint", "ivhh_touchpoint", contact.getUser_phone(), contact.getTransactionDate(),contact.getUser_email(),contact.getFirstName(),contact.getName(),contact.getBookingDate(),contact.getSentDate(),"FALSE"
					,contact.getTag_field1(),contact.getTag_field2(),contact.getTag_field3(),contact.getTag_field4(),contact.getTag_field6(),contact.getTag_field8()
					,contact.getTag_field9(),contact.getTag_field10(),contact.getTag_field11(),contact.getTag_field13(),contact.getTag_field14()
					,contact.getTag_field15(),contact.getTag_field16(),contact.getTag_field18(),contact.getTag_field20(),contact.getSurveyType(),contact.getPropertyName(),contact.getProjectName()});
		}

        return list;
    }

}
