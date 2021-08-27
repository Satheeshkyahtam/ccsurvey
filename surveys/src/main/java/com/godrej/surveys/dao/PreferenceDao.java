package com.godrej.surveys.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.godrej.surveys.dto.Preference;
import com.godrej.surveys.sqlprovider.PreferenceSQLProvider;

@Mapper
public interface PreferenceDao {

	@InsertProvider(type = PreferenceSQLProvider.class, method = "getInsertQuery")
	@Options(useGeneratedKeys=true,keyProperty = "surveyPreferenceId", keyColumn = "g_survey_preference_id"
			)
	public Integer insert(Preference preference);
	
	@InsertProvider(type = PreferenceSQLProvider.class, method = "getUpdateQuery")
	public Integer update(Preference preference);
	
	@InsertProvider(type = PreferenceSQLProvider.class, method = "getSelectQuery")
	public Preference select(Preference preference);
}
