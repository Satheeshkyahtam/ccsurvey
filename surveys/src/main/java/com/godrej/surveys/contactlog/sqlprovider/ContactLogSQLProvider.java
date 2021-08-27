package com.godrej.surveys.contactlog.sqlprovider;

import java.util.Map;

import com.godrej.surveys.dto.BookingParam;

public class ContactLogSQLProvider {

	public String duplicateMarkerQuery() {
		StringBuilder query =  new StringBuilder();
		query.append("UPDATE rmsurvey.g_contacts SET repeated = true WHERE name IN (") 
		.append(" SELECT name FROM (")  
		.append("   Select row_number () over (partition BY email order by bookingdate,firstname,name ASC) updateId ") 
		.append("   ,name ") 
		.append("   from rmsurvey.g_contacts") 
		.append("   WHERE instance_id = #{instanceId} ) x WHERE x.updateId>1 )")
		.append(" AND  instance_id = #{instanceId}");
		return query.toString();
	}

	public String duplicateMarkerQueryForRM() {
		StringBuilder query =  new StringBuilder();
		query.append("UPDATE rmsurvey.g_contacts SET repeated = true WHERE name IN (") 
		.append(" SELECT name FROM (")  
		.append("   Select row_number () over (partition BY email, field20 order by bookingdate,firstname,name ASC) updateId ") 
		.append("   ,name ") 
		.append("   from rmsurvey.g_contacts") 
		.append("   WHERE instance_id = #{instanceId} ) x WHERE x.updateId>1 )")
		.append(" AND  instance_id = #{instanceId}");
		return query.toString();
	}
	
	public String markContactsQuery() {
		StringBuilder query =  new StringBuilder();
		query.append(" UPDATE rmsurvey.g_contacts a SET ")
		.append(" survey_sent = true ")
		.append(" WHERE instance_id = #{instanceId} ");
		return query.toString();
	}
	
	public String updateSentContactsQuery(Map<String,BookingParam> params) {
		BookingParam bookingParam = params.get("param1");
		String []bookings = bookingParam.getBookings();
		String instanceId = bookingParam.getInstanceId();
		StringBuilder query =  new StringBuilder();
		query.append("UPDATE rmsurvey.g_contacts a SET ") 
		.append(" survey_sent_date= now()")
		.append(" WHERE instance_id = '") 
		.append(instanceId)
		.append("' AND a.name IN ("); 
			for(int i = 0;i<bookingParam.getBookings().length; i++) {
				if (i > 0) {
					query.append(',');
			    }
				query.append("'").append(bookings[i]);
				query.append("'");
			}
			query.append(" )");
		return query.toString();
	}
}
