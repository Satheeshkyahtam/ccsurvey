package com.godrej.surveys.baseline.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.godrej.surveys.baseline.dto.BaselineSurveyContactDto;
import com.godrej.surveys.util.AppConstants;
import com.godrej.surveys.util.DateUtil;

public class UpdateBaselineSurveyField {
	static Logger logger = LoggerFactory.getLogger(UpdateBaselineSurveyField.class);
	@Autowired
	private static DateUtil dateUtil;
	
	//static Logger logger = Logger.getLogger(OfferPrePayments.class);
	
	static String USERNAME = AppConstants.SFDC_USERNAME; 
	static String PASSWORD = AppConstants.SFDC_PASSWORD;
	static String LOGINURL = AppConstants.SFDC_LOGINURL;
	static String GRANTSERVICE =AppConstants.SFDC_GRANTSERVICE;
	static String CLIENTID = AppConstants.SFDC_CLIENTID;
	static String CLIENTSECRET = AppConstants.SFDC_CLIENTSECRET;
	

	public static String updateBaselineData(List<BaselineSurveyContactDto> ecData1)  {
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String loginURL = LOGINURL +
				GRANTSERVICE +
		"&client_id=" + CLIENTID +
		"&client_secret=" + CLIENTSECRET +
		"&username=" + USERNAME +
		"&password=" + PASSWORD;
		HttpPost httpPost = new HttpPost(loginURL);
		HttpResponse response = null;
		
		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException cpException) {
			logger.error("Error :",cpException);
		} catch (IOException ioException) {
			logger.error("Error :",ioException);
		}
		final int statusCode =
		response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			logger.error("Error authenticating to Force.com:"+statusCode);
			return "";
		}
		
		String getResult = null;
		try {
			getResult = EntityUtils.toString(response.getEntity());
		} catch (IOException ioException) {
		}
		
		JSONObject jsonObject = null;
		
		String loginAccessToken = null;
		String loginInstanceUrl = null;
		
		try {
			jsonObject = (JSONObject) new
			JSONTokener(getResult).nextValue();
			loginAccessToken = jsonObject.getString("access_token");
			loginInstanceUrl = jsonObject.getString("instance_url");
		} catch (JSONException jsonException) {
		}
		
		
		//logger.info(response.getStatusLine());
		logger.info("Successful login");
		logger.info(" instance URL: "+loginInstanceUrl);
		logger.info(" access token/session ID:"+loginAccessToken);
		String testVal="";
		try {
			testVal = updateBaselineJson(loginAccessToken,ecData1);
			
			logger.info("Response of Prepayemnt :::: " + testVal);
			
			httpPost.releaseConnection();
			
			return testVal;
			
		} catch (ServletException e) {
			logger.error("Error of Servlet: ",e);
		} catch (IOException e) {
			logger.error("Error of IO : ",e);
		}
		return testVal;
	}

 

	private static String updateBaselineJson(String loginAccessToken,List<BaselineSurveyContactDto> ecData1) throws ServletException, IOException {
		String accountId = null;
		CloseableHttpClient  httpclient = HttpClients.createDefault();
		JSONArray ja = new JSONArray();
		
		
		try {
				//LocalDate currentDate = new LocalDate();
				//Date date = currentDate.toDateTimeAtStartOfDay().toDate();
				//Date dateAdd7 = currentDate.toDateTimeAtStartOfDay().toDate();
				//SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
				//String strDate = sm.format(date);
				
				//String transactionDate = dateUtil.getCurrentDate("yyyy-MM-dd");
			
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				String transactionDate = dtf.format(now);
				
				for(int i=0;i<ecData1.size();i++) 
				{
					JSONObject account = new JSONObject();
					account.put("appBookId",ecData1.get(i).getSfid());
					account.put("surveySentDate",transactionDate);  
					account.put("surveySent",true);  
					ja.put(account);
				}
				logger.info("Final Creat Offer Data:-"+ja);
		}
		catch (JSONException e) {
			logger.error("Error 165: ",e);
			throw new ServletException(e);
		}
		
		HttpPost httpost = new HttpPost(AppConstants.SFDC_UPDATEBASELINESURVEY);
	
		httpost.addHeader("Authorization", "OAuth " + loginAccessToken);
		StringEntity messageEntity = new StringEntity(ja.toString(), ContentType.create("application/json"));
		httpost.setEntity(messageEntity);
	
		CloseableHttpResponse  closeableresponse = httpclient.execute(httpost);
		logger.info("Response Status line :" + closeableresponse.getStatusLine());
	
		String getResult = null;
        try {
			getResult = EntityUtils.toString(closeableresponse.getEntity());
			logger.info("getResult of Prepayment API Response:" + getResult);
        } catch (IOException ioException) {
        	logger.error("Error : ",ioException);
        }
	        
        //JSONObject jsonObject = null;
        String loginInstanceUrl = null;
        
        /*try {
        	logger.info("ID:---**************");
        	logger.info("jsonObjectsssss:---"+jsonObject);
             JSONArray jsonArr = new JSONArray(getResult);
             for (int i = 0; i < jsonArr.length(); i++)
             {
                 JSONObject jsonObj = jsonArr.getJSONObject(i);
                 logger.info("ds"+jsonObj.get("Name")); 
             }
        } catch (JSONException jsonException) {
        	logger.error("Error : ",jsonException);
        }*/
        
        //logger.info(closeableresponse.getStatusLine());
        logger.info("Successful login");
        logger.info("  instance URL: "+loginInstanceUrl);
        logger.info("  access token/session ID: "+loginAccessToken);
		
		try {
			logger.info("HTTP status " + closeableresponse.getStatusLine().getStatusCode() + " Agent Status Updated\n\n");
		}
		finally {
			httpclient.close();
		}
		
		
		logger.info("getResult of Prepayment API ::: " + getResult);
		
		
		return getResult;
		} 
}