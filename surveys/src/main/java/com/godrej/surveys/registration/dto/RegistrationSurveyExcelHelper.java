package com.godrej.surveys.registration.dto;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVWriter;

public class RegistrationSurveyExcelHelper {

	public static ByteArrayInputStream tutorialsToExcel(List<RegistrationSurveyContactDto> contacts, String instanceId) throws Exception {
		
		List<String[]> csvData = createCsvDataForSurvey(contacts);

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("D:\\Satheesh\\Projects\\Litmus World\\Registration\\"+instanceId+".csv"))) {
            writer.writeAll(csvData);
        }
		return null;
		
	}
	private static List<String[]> createCsvDataForSurvey(List<RegistrationSurveyContactDto> contacts) {
        String[] header = { "Touchpoint", "app_id", "user_phone", "transactionDate","user_email","cust_name","name","bookingDate"
  			  ,"sentDate","sentStatus","field1","field2","field3","field4","field6","field8","field9","field10","field11",
  			  "field13","field14","field15","field16","field18","field20","surveyType","propertyName","projectName"};
        List<String[]> list = new ArrayList<>();
        list.add(header);
        /*for (OnboardingSurveyContactDto contact : contacts) {
        	 list.add(record1);
        }*/
        Iterator<RegistrationSurveyContactDto> it = contacts.iterator();
		while (it.hasNext()) {
			RegistrationSurveyContactDto contact = it.next();
			list.add(new String[] { "Touchpoint", "3qwp_touchpoint", contact.getUser_phone(), contact.getTransactionDate(),contact.getUser_email(),contact.getFirstName(),contact.getName(),contact.getBookingDate(),contact.getSentDate(),"FALSE"
					,contact.getTag_field1(),contact.getTag_field2(),contact.getTag_field3(),contact.getTag_field4(),contact.getTag_field6(),contact.getTag_field8()
					,contact.getTag_field9(),contact.getTag_field10(),contact.getTag_field11(),contact.getTag_field13(),contact.getTag_field14()
					,contact.getTag_field15(),contact.getTag_field16(),contact.getTag_field18(),contact.getTag_field20(),contact.getSurveyType(),contact.getPropertyName(),contact.getProjectName()});
		}

        return list;
    }
	
	/*public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "Touchpoint", "app_id", "user_phone", "transactionDate","user_email","cust_name","name","bookingDate"
			  ,"sentDate","sentStatus","field1","field2","field3","field4","field6","field8","field9","field10","field11",
			  "field13","field14","field15","field16","field18","field20","surveyType","propertyName","projectName"};
	  static String SHEET = "Contacts";

	  public static ByteArrayInputStream tutorialsToExcel(List<OnboardingSurveyContactDto> contacts, String instanceId) {
	    try (Workbook workbook = new HSSFWorkbook(); 
	    		ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);

	      // Header
	      Row headerRow = sheet.createRow(0);

	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }

	      int rowIdx = 1;
	      for (OnboardingSurveyContactDto contact : contacts) {
	        Row row = sheet.createRow(rowIdx++);

	        row.createCell(0).setCellValue("Touchpoint");
	        row.createCell(1).setCellValue("givs_touchpoint");
	        row.createCell(2).setCellValue(contact.getUser_phone());
	        row.createCell(3).setCellValue(contact.getTransactionDate());
	        
	        row.createCell(4).setCellValue(contact.getUser_email());
	        row.createCell(5).setCellValue(contact.getFirstName());
	        row.createCell(6).setCellValue(contact.getName());
	        row.createCell(7).setCellValue(contact.getBookingDate());
	        row.createCell(8).setCellValue(contact.getSentDate());
	        row.createCell(9).setCellValue("FALSE");
	        row.createCell(10).setCellValue(contact.getTag_field1());
	        row.createCell(11).setCellValue(contact.getTag_field2());
	        row.createCell(12).setCellValue(contact.getTag_field3());
	        row.createCell(13).setCellValue(contact.getTag_field4());
	        row.createCell(14).setCellValue(contact.getTag_field6());
	        row.createCell(15).setCellValue(contact.getTag_field8());
	        row.createCell(16).setCellValue(contact.getTag_field9());
	        row.createCell(16).setCellValue(contact.getTag_field10());
	        row.createCell(16).setCellValue(contact.getTag_field11());
	        
	        row.createCell(16).setCellValue(contact.getTag_field13());
	        row.createCell(16).setCellValue(contact.getTag_field14());
	        row.createCell(16).setCellValue(contact.getTag_field15());
	        row.createCell(16).setCellValue(contact.getTag_field16());
	        row.createCell(16).setCellValue(contact.getTag_field18());
	        row.createCell(16).setCellValue(contact.getTag_field20());
	        
	        row.createCell(16).setCellValue(contact.getSurveyType());
	        row.createCell(16).setCellValue(contact.getPropertyName());
	        
	      }

	      for(int i = 0; i < HEADERs.length; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        // Write the output to a file
	        FileOutputStream fileOut = new FileOutputStream("D:\\Satheesh\\Projects\\Litmus World\\GPL-custonboarding-survey-"+instanceId+".csv");
	        workbook.write(fileOut);
	        fileOut.close();

	        // Closing the workbook
	        workbook.close();
	      
	     // workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	  }*/
}
