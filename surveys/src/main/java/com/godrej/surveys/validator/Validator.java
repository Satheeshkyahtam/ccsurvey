package com.godrej.surveys.validator;

import com.godrej.surveys.dto.Errors;

/**
 * Object Validator
 * @author Vivek
 *
 */
public interface Validator {

	public void validate(Object object, Errors errors);
}
