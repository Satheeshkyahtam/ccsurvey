package com.godrej.surveys.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.dto.APILoggerDto;
import com.godrej.surveys.sqlprovider.APILoggerSQLProvider;

@Mapper
public interface APILoggerDao {

	@InsertProvider(type = APILoggerSQLProvider.class, method = "getInsertSQL")
	public int insert(APILoggerDto logger);
	
	@UpdateProvider(type = APILoggerSQLProvider.class, method = "getUpdateResponseSQL")
	public int updateResponse(APILoggerDto logger);
}
