package com.godrej.surveys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godrej.surveys.dao.PreferenceDao;
import com.godrej.surveys.dto.Preference;
import com.godrej.surveys.service.PreferenceService;

@Service
public class PreferenceServiceImpl implements PreferenceService{

	@Autowired
	private PreferenceDao preferenceDao;
	
	@Override
	public Integer insert(Preference preference) {
		return preferenceDao.insert(preference);

	}

	@Override
	public Integer update(Preference preference) {
		return preferenceDao.update(preference);
	}

	@Override
	public Preference select(Preference preference) {
		return preferenceDao.select(preference);
	}

}
