package com.godrej.surveys.service;

import com.godrej.surveys.dto.Preference;

public interface PreferenceService {

	public Integer insert(Preference preference);
	
	public Integer update(Preference preference);
	
	public Preference select (Preference preference);
}
