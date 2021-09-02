package com.straightsixstudios.utility.validation;

import com.straightsixstudios.utility.validation.exception.FieldValidationException;
import com.straightsixstudios.utility.validation.output.ValidationResult;

/**
 * @author charles
 */
public interface Validator {

    <I> ValidationResult validate(I input) throws FieldValidationException;

}
