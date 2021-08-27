package com.godrej.surveys.sqlprovider;

public class APILoggerSQLProvider {

	public String getInsertSQL() {
		StringBuilder sql =  new StringBuilder();
		sql.append("INSERT INTO rmsurvey.nv_apilogger (request,response)")
		.append(" values (#{request}, #{response})");
		return sql.toString();
	}
	
	public String getUpdateResponseSQL() {
		StringBuilder sql =  new StringBuilder();
		sql.append("UPDATE rmsurvey.nv_apilogger SET response =")
		.append(" #{response) WHERE nv_apilogger_id = " )
		.append("#{apiLoggerId}");
		return sql.toString();
	}
}
