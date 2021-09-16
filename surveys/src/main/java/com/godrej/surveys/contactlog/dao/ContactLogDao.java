package com.godrej.surveys.contactlog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import com.godrej.surveys.contactlog.sqlprovider.ContactLogSQLProvider;
import com.godrej.surveys.dto.BookingParam;
import com.godrej.surveys.dto.ProjectDto;

@Mapper
public interface ContactLogDao {

	@UpdateProvider(type = ContactLogSQLProvider.class, method = "duplicateMarkerQuery")
	public Integer markDuplicate(ProjectDto project);

	@UpdateProvider(type = ContactLogSQLProvider.class, method = "duplicateMarkerQueryForRM")
	public Integer markDuplicateForRM(ProjectDto project);

	@UpdateProvider(type = ContactLogSQLProvider.class, method = "markContactsQuery")
	public Integer markContactLogs(ProjectDto project);
	
	@UpdateProvider(type = ContactLogSQLProvider.class, method = "updateSentContactsQuery")
	public Integer updateSentContacts(@Param("bookingParam") BookingParam bookingParam);
	
	/* Added by A */
	@UpdateProvider(type = ContactLogSQLProvider.class, method = "updateBaselineSentContactsQuery")
	public Integer updateBaselineSentContacts(@Param("bookingParam") BookingParam bookingParam);
	/* END Added by A */
}
