package com.godrej.surveys.rm.sqlprovider;

public class RMSurveySQLProvider {

	StringBuilder baseQuery = new StringBuilder();
	public RMSurveySQLProvider() {


		baseQuery.append("SELECT a.propstrength__applicant_email__c as email,a.rm_name__c field20 ")
				.append(",a.propstrength__applicant_mobile__c as mobile, ")
				.append("a.propstrength__primary_applicant_name__c as firstName, '' as lastName")
				.append(",a.propstrength__booking_date__c dateOfBooking")
				.append(",to_char(a.propstrength__booking_date__c,'dd-mm-yyyy') bookingDate ")
				.append(",a.name, #{segmentCode} as segmentCode ")
				.append(", #{transactionDate} as transactionDate ")
				.append(",a.propstrength__property_name__c as field1,Project_Name__c as field15 ")
				.append(",a.sap_customer_code__c AS field2 ")
				.append(",b.propstrength__income_tax_permanent_account_no__c as field4")
				.append(",b.mailing_city__c as field6")
				.append(", #{region} AS field8")
				.append(",a.Project_Phases__c as field9")
				.append(",a.PropStrength__Welcome_Letter_Note__c as field11")
				.append(",a.Handover_Date__c as field13")
				.append(",a.SiteVisitOtherThanHandoverReceivedDate__c as field14")
				.append(",b.FirstName as field16")
				.append(" ,'' AS surveyType")
				.append(",propstrength__property_name__c AS propertyName")
				.append(",'ECRM' AS field3 ")
				.append(",#{surveyId} as field18 ")
				.append(", handover_done_by__c as field7 ")
				.append(",#{instanceId} as instance_id ")
				.append(", now() as created_on ")
				.append(", #{name} as survey ")
				/*
				 * .append(" , row_number () over (partition BY x.email ") .append(" ORDER BY ")
				 * .append(" x.propstrength__booking_date__c,")
				 * .append(" x.firstname, x.name ASC) as batchId ")
				 * .append(" , row_number () over (partition BY x.email,x.custom20 ")
				 * .append(" ORDER BY ") .append(" x.propstrength__booking_date__c,")
				 * .append(" x.firstname, x.name ASC) as updateId ")
				 */
				.append(" FROM salesforce.propstrength__application_booking__c a ")
			    .append(" INNER JOIN salesforce.contact b on (a.propstrength__primary_customer__c =b.sfid)");
	}	 
	
	
	
	public final String getBasicWhereClause() {
		StringBuilder whereClause = new StringBuilder();
		 whereClause.append(" WHERE A.propstrength__project__c=#{sfid} ")
			.append("AND ( a.handover_date__c IS NULL OR ")
			.append(" a.handover_date__c >= (date(to_char(now(),'mm-dd-yyyy'))- integer'60')) ")
			.append(" AND A.propstrength__booking_date__c <= (date(to_char(now(),'mm-dd-yyyy'))- integer'15') ")
			.append("AND A.propstrength__active__c=true AND ")
			.append(" a.dnd__c= false ")
			.append("  AND a.propstrength__status__c= 'Deal Approved'")
			.append(" AND (a.customer_status__c IS NULL OR ")
			.append(" A.customer_status__c NOT IN ('Legal Case','Voluntary Cancellation Request Received','Pre-termination sent','Termination sent','Registration Termination') ")
			.append(" ) AND A.rm_name__c is not null")
			.append(" AND a.propstrength__applicant_email__c is not null ")
			.append(" AND a.S_RMASSD__c is null AND a.S_RMASS__c <> true ")
			.append(" ORDER BY a.propstrength__booking_date__c,firstname, a.name ASC  ");
		return whereClause.toString();
	}

	
	public final String getContactSQL() {
		StringBuilder query = new StringBuilder();
		query.append(baseQuery.toString())
			.append(getBasicWhereClause());
		return query.toString();
	}	
	
	
	public final String getContactQuery() {
		StringBuilder query =  new StringBuilder();
		query.append(baseQuery.toString())
		.append(" WHERE a.propstrength__project__c=#{sfid} AND a.name IN ")
		.append(" ( SELECT name from rmsurvey.g_contacts where instance_id  = #{instanceId} and (repeated is null OR repeated =false) ) ");
		return query.toString();
	}

	public final String parkRecords() {
		StringBuilder query =  new StringBuilder();
		query.append(" INSERT INTO rmsurvey.g_contacts (email,field20 ,mobile ,firstName,lastName ,bookingDate , bookingDateStr ,") 
		.append("	name,segmentCode ,transactionDate , field1 ,field15 ,field2 ,field4 ,field6 ,")
		.append("	field8 ,field9 ,field11 ,field13 ,field14, field16 ,surveyType ,propertyName ,") 
		.append("	field3,field18 ,field7,instance_id ,created_on,survey ) ")
		.append(getContactSQL());	
		return query.toString();
	}
	
	public final String getFirstReminderSQLFromStaging() {
		StringBuilder query =  new StringBuilder();
		query.append(baseQuery.toString())
		.append(" WHERE a.name IN ")
		.append(" ( SELECT name from rmsurvey.g_contacts where field15  = #{name} and (repeated is null OR repeated =false) ) ")
		.append("");
		return query.toString();
	}
	
	public final String getFirstReminderSQL() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a.propstrength__applicant_email__c as email,a.rm_name__c custom20 ")
		.append(",a.propstrength__applicant_mobile__c as mobile, ")
		.append("a.propstrength__primary_applicant_name__c as firstName, '' as lastName")
		.append(",to_char(a.propstrength__booking_date__c,'dd-mm-yyyy') bookingDate ")
		.append(",a.name, #{segmentCode} as segmentCode ")
		.append(",a.name, #{transactionDate} as transactionDate ")
		.append(",a.sfid ")
		.append(",a.propstrength__property_name__c as field1,Project_Name__c as field15 ")
		.append(",a.sap_customer_code__c AS field2 ")
		.append(",b.propstrength__income_tax_permanent_account_no__c as field4")
		.append(",b.mailing_city__c as field6")
		.append(", #{region} AS field8")
		.append(",a.Project_Phases__c as field9")
		.append(",a.PropStrength__Welcome_Letter_Note__c as field11")
		.append(",a.Handover_Date__c as field13")
		.append(",a.SiteVisitOtherThanHandoverReceivedDate__c as field14")
		.append(",a.Project_Name__c as field15")
		.append(",b.FirstName as field16")
		.append(",'ECRM' AS field3 ")
		.append(",#{surveyId} as field18 ")
		.append(" FROM salesforce.propstrength__application_booking__c a " )
	    .append(" INNER JOIN salesforce.contact b on (a.propstrength__primary_customer__c =b.sfid)")
		.append( " WHERE A.propstrength__project__c=#{sfid} ")
		.append("AND ( a.handover_date__c IS NULL OR ")
		.append(" a.handover_date__c >= (date(to_char(now(),'mm-dd-yyyy'))- integer'62')) ")
		.append(" AND A.propstrength__booking_date__c <= (date(to_char(now(),'mm-dd-yyyy'))- integer'17') ")
		.append("AND A.propstrength__active__c=true AND ")
		.append(" a.dnd__c= false ")
		.append("  AND a.propstrength__status__c= 'Deal Approved'")
		.append(" AND (a.customer_status__c IS NULL OR ")
		.append(" A.customer_status__c NOT IN ('Legal Case','Voluntary Cancellation Request Received','Pre-termination sent','Termination sent','Registration Termination') ")
		.append(" ) AND A.rm_name__c is not null")
		.append(" AND a.propstrength__applicant_email__c is not null ")
		.append(" AND a.S_RMASS__c IS NOT NULL AND S_RMASSD__c =date(to_char(now(),'mm-dd-yyyy'))- integer'2' ")
		.append(" AND a.S_RMASFRS__c IS NULL ")
		.append(" ORDER BY a.propstrength__booking_date__c, firstName,a.name ASC  ");
		return query.toString();	
	}
	
	public final String getFirstReminderSQLDyno() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a.propstrength__applicant_email__c as email,a.rm_name__c custom20 ")
		.append(",a.propstrength__applicant_mobile__c as mobile, ")
		.append("a.propstrength__primary_applicant_name__c as firstName, '' as lastName")
		.append(",to_char(a.propstrength__booking_date__c,'dd-mm-yyyy') bookingDate ")
		.append(",a.name, #{segmentCode} as segmentCode ")
		.append(",a.name, #{transactionDate} as transactionDate ")
		.append(",a.sfid ")
		.append(",a.propstrength__property_name__c as field1,Project_Name__c as field15 ")
		.append(",a.sap_customer_code__c AS field2 ")
		.append(",b.propstrength__income_tax_permanent_account_no__c as field4")
		.append(",b.mailing_city__c as field6")
		.append(", #{region} AS field8")
		.append(",a.Project_Phases__c as field9")
		.append(",a.PropStrength__Welcome_Letter_Note__c as field11")
		.append(",a.Handover_Date__c as field13")
		.append(",a.SiteVisitOtherThanHandoverReceivedDate__c as field14")
		.append(",a.Project_Name__c as field15")
		.append(",b.FirstName as field16")
		.append(",'ECRM' AS field3 ")
		.append(",#{surveyId} as field18 ")
		.append(" FROM salesforce.propstrength__application_booking__c a " )
	    .append(" INNER JOIN salesforce.contact b on (a.propstrength__primary_customer__c =b.sfid)")
		.append( " WHERE A.propstrength__project__c=#{sfid} ")
		.append("AND ( a.handover_date__c IS NULL OR ")
		.append(" a.handover_date__c >= (date(to_char(now(),'mm-dd-yyyy'))- integer'62')) ")
		.append(" AND A.propstrength__booking_date__c <= (date(to_char(now(),'mm-dd-yyyy'))- integer'17') ")
		.append("AND A.propstrength__active__c=true AND ")
		.append(" a.dnd__c= false ")
		.append("  AND a.propstrength__status__c= 'Deal Approved'")
		.append(" AND (a.customer_status__c IS NULL OR ")
		.append(" A.customer_status__c NOT IN ('Legal Case','Voluntary Cancellation Request Received','Pre-termination sent','Termination sent','Registration Termination') ")
		.append(" ) AND A.rm_name__c is not null")
		.append(" AND a.propstrength__applicant_email__c is not null ")
		.append(" AND a.S_RMASS__c IS NOT NULL AND S_RMASSD__c =date(to_char(now(),'mm-dd-yyyy'))- #{intervalP} ")
		.append(" AND a.S_RMASFRS__c IS NULL ")
		.append(" ORDER BY a.propstrength__booking_date__c, firstName,a.name ASC  ");
		return query.toString();	
	}
	
	public final String getSecondReminderSQL() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a.propstrength__applicant_email__c as email,a.rm_name__c custom20 ")
		.append(",a.propstrength__applicant_mobile__c as mobile, ")
		.append("a.propstrength__primary_applicant_name__c as firstName, '' as lastName")
		.append(",to_char(a.propstrength__booking_date__c,'dd-mm-yyyy') bookingDate ")
		.append(",a.name, #{segmentCode} as segmentCode ")
		.append(",a.name, #{transactionDate} as transactionDate ")
		.append(",a.sfid ")
		.append(",a.propstrength__property_name__c as field1,Project_Name__c as field15 ")
		.append(",a.sap_customer_code__c AS field2 ")
		.append(",b.propstrength__income_tax_permanent_account_no__c as field4")
		.append(",b.mailing_city__c as field6")
		.append(", #{region} AS field8")
		.append(",a.Project_Phases__c as field9")
		.append(",a.PropStrength__Welcome_Letter_Note__c as field11")
		.append(",a.Handover_Date__c as field13")
		.append(",a.SiteVisitOtherThanHandoverReceivedDate__c as field14")
		.append(",a.Project_Name__c as field15")
		.append(",b.FirstName as field16")
		.append(",'ECRM' AS field3 ")
		.append(",#{surveyId} as field18 ")
		.append(" FROM salesforce.propstrength__application_booking__c a " )
	    .append(" INNER JOIN salesforce.contact b on (a.propstrength__primary_customer__c =b.sfid)")
		.append( " WHERE A.propstrength__project__c=#{sfid} ")
		.append("AND ( a.handover_date__c IS NULL OR ")
		.append(" a.handover_date__c >= (date(to_char(now(),'mm-dd-yyyy'))- integer'64')) ")
		.append(" AND A.propstrength__booking_date__c <= (date(to_char(now(),'mm-dd-yyyy'))- integer'19') ")
		.append("AND A.propstrength__active__c=true AND ")
		.append(" a.dnd__c= false ")
		.append("  AND a.propstrength__status__c= 'Deal Approved'")
		.append(" AND (a.customer_status__c IS NULL OR ")
		.append(" A.customer_status__c NOT IN ('Legal Case','Voluntary Cancellation Request Received','Pre-termination sent','Termination sent','Registration Termination') ")
		.append(" ) AND A.rm_name__c is not null")
		.append(" AND a.propstrength__applicant_email__c is not null ")
		.append(" AND a.S_RMASS__c IS NOT NULL AND S_RMASSD__c =date(to_char(now(),'mm-dd-yyyy'))- integer'4' ")
		.append(" AND a.S_RMASSRS__c IS NULL ")
		.append(" ORDER BY a.propstrength__booking_date__c, firstName,a.name ASC  ");
		return query.toString();	
	}
	
	
	public final String getSecondReminderSQLDyno() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a.propstrength__applicant_email__c as email,a.rm_name__c custom20 ")
		.append(",a.propstrength__applicant_mobile__c as mobile, ")
		.append("a.propstrength__primary_applicant_name__c as firstName, '' as lastName")
		.append(",to_char(a.propstrength__booking_date__c,'dd-mm-yyyy') bookingDate ")
		.append(",a.name, #{segmentCode} as segmentCode ")
		.append(",a.name, #{transactionDate} as transactionDate ")
		.append(",a.sfid ")
		.append(",a.propstrength__property_name__c as field1,Project_Name__c as field15 ")
		.append(",a.sap_customer_code__c AS field2 ")
		.append(",b.propstrength__income_tax_permanent_account_no__c as field4")
		.append(",b.mailing_city__c as field6")
		.append(", #{region} AS field8")
		.append(",a.Project_Phases__c as field9")
		.append(",a.PropStrength__Welcome_Letter_Note__c as field11")
		.append(",a.Handover_Date__c as field13")
		.append(",a.SiteVisitOtherThanHandoverReceivedDate__c as field14")
		.append(",a.Project_Name__c as field15")
		.append(",b.FirstName as field16")
		.append(",'ECRM' AS field3 ")
		.append(",#{surveyId} as field18 ")
		.append(" FROM salesforce.propstrength__application_booking__c a " )
	    .append(" INNER JOIN salesforce.contact b on (a.propstrength__primary_customer__c =b.sfid)")
		.append( " WHERE A.propstrength__project__c=#{sfid} ")
		.append("AND ( a.handover_date__c IS NULL OR ")
		.append(" a.handover_date__c >= (date(to_char(now(),'mm-dd-yyyy'))- integer'64')) ")
		.append(" AND A.propstrength__booking_date__c <= (date(to_char(now(),'mm-dd-yyyy'))- integer'19') ")
		.append("AND A.propstrength__active__c=true AND ")
		.append(" a.dnd__c= false ")
		.append("  AND a.propstrength__status__c= 'Deal Approved'")
		.append(" AND (a.customer_status__c IS NULL OR ")
		.append(" A.customer_status__c NOT IN ('Legal Case','Voluntary Cancellation Request Received','Pre-termination sent','Termination sent','Registration Termination') ")
		.append(" ) AND A.rm_name__c is not null")
		.append(" AND a.propstrength__applicant_email__c is not null ")
		.append(" AND a.S_RMASS__c IS NOT NULL AND S_RMASSD__c =date(to_char(now(),'mm-dd-yyyy'))- #{intervalP2} ")
		.append(" AND a.S_RMASSRS__c IS NULL ")
		.append(" ORDER BY a.propstrength__booking_date__c, firstName,a.name ASC  ");
		return query.toString();	
	}
	
	
	public String getStatusUpdateQuery() {
		StringBuilder query =  new StringBuilder();
		query.append("UPDATE salesforce.propstrength__application_booking__c a SET ") 
			.append("S_RMASSD__c= now(),S_RMASS__c=true")
			.append(" WHERE ") 
			.append("a.propstrength__project__c= #{sfid} ") 
			.append("AND ( a.handover_date__c IS NULL OR ") 
			.append("a.handover_date__c >= (date(to_char(now(),'mm-dd-yyyy'))- integer'60')) ") 
			.append(" AND a.propstrength__booking_date__c <= (date(to_char(now(),'mm-dd-yyyy'))- integer'15')") 
			.append(" AND a.propstrength__active__c=true AND ") 
			.append("a.dnd__c= false " ) 
			.append("AND a.propstrength__status__c= 'Deal Approved' ") 
			.append("AND (a.customer_status__c IS NULL OR ") 
			.append(" a.customer_status__c NOT IN ('Legal Case','Voluntary Cancellation Request Received'") 
			.append(" ,'Pre-termination sent','Termination sent','Registration Termination') ") 
			.append(" ) AND a.rm_name__c is not null ") 
			.append(" AND a.propstrength__applicant_email__c is not null ") 
			.append("AND a.S_RMASSD__c IS NULL AND a.S_RMASS__c <> true");
		return query.toString();
	}

	public String getStatusUpdateFromContactLogsQuery() {
		StringBuilder query =  new StringBuilder();
		query.append("UPDATE salesforce.propstrength__application_booking__c a SET ") 
			.append(" S_RMASSD__c = survey_sent_date")
			.append(",S_RMASS__c= survey_sent")
			.append(" FROM rmsurvey.g_contacts b ")
			.append( " Where a.name = b.name and b.instance_id = #{instanceId}");

		return query.toString();
	}
	
	public String getUpdateFirstReminderStatusSQL() {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE salesforce.propstrength__application_booking__c")
		.append(" SET S_RMASFRS__c=now()")
		.append(" WHERE sfid=#{sfid}");	
		return query.toString();
	}
	
	public String getUpdateSecondReminderStatusSQL() {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE salesforce.propstrength__application_booking__c")
		.append(" SET S_RMASSRS__c=now()")
		.append(" WHERE sfid=#{sfid}");	
		return query.toString();
	}
	
	
	public String getClearingUpdateQuery() {
		StringBuilder query =  new StringBuilder();
		query.append("UPDATE salesforce.propstrength__application_booking__c SET ") 
			.append("  S_RMASSD__c =null WHERE sfid in ( SELECT sfid FROM (") 
				.append(" SELECT row_number () over (partition BY x.email") 
				.append(" ,x.custom20 ") 
				.append(" ORDER BY ") 
				.append(" x.propstrength__booking_date__c,") 
				.append(" x.firstname, x.name ASC) as updateId ") 
				.append(" , x.*  FROM ") 
				.append(" (SELECT ") 
				.append(" a.propstrength__applicant_email__c                   AS email,") 
				.append(" a.propstrength__applicant_mobile__c                  AS mobile,") 
				.append(" a.propstrength__primary_applicant_name__c            AS firstname, ") 
				.append(" '' AS lastname, ") 
				.append(" to_char(a.propstrength__booking_date__c, 'dd-mm-yyyy') bookingdate, ") 
				.append(" a.name, ") 
				.append(" a.sfid, ") 
				.append(" a.propstrength__property_name__c                     AS field1, ") 
				.append(" a.propstrength__booking_date__c ")
				.append(",a.rm_name__c custom20")
				.append(" FROM ")
				.append(" salesforce.propstrength__application_booking__c   a ") 
				.append(" INNER JOIN salesforce.contact  b ON ( a.propstrength__primary_customer__c = b.sfid ) ") 
				.append(" WHERE ")  
				.append(" a.propstrength__project__c = #{sfid} ") 
				.append(" AND ( a.handover_date__c IS NULL OR a.handover_date__c >= ( date(to_char(now(), 'mm-dd-yyyy')) - integer'60')) ") 
				.append(" AND A.propstrength__booking_date__c <= (date(to_char(now(),'mm-dd-yyyy'))- integer'15') ")
				.append(" AND a.propstrength__active__c = true ") 
				.append(" AND a.dnd__c = false ") 
				.append(" AND a.propstrength__status__c = 'Deal Approved' ") 
				.append(" AND ( a.customer_status__c IS NULL ") 
				.append(" OR a.customer_status__c NOT IN ( ") 
				.append(" 'Legal Case', ") 
				.append(" 'Voluntary Cancellation Request Received',") 
				.append(" 'Pre-termination sent', ") 
				.append(" 'Termination sent',") 
				.append(" 'Registration Termination'") 
				.append(" ) ) AND a.propstrength__applicant_email__c IS NOT NULL ")
				.append(" AND a.rm_name__c is not null ")
				.append(" AND a.S_RMASS__c = true ") 
				.append(" AND a.S_RMASSD__c = CURRENT_DATE")
				.append(" ORDER BY ") 
				.append(" a.propstrength__booking_date__c, ")  
				.append(" a.propstrength__primary_applicant_name__c, a.name ASC ") 
				.append(" ) X   ) Z WHERE updateId >1 )");
		
		return query.toString();
	}
}
