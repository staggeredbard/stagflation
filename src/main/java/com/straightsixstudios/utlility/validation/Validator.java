package com.straightsixstudios.utlility.validation;

import com.straightsixstudios.utlility.validation.exception.FieldValidationException;

/**
 * @author charles
 */
public interface Validator {

    <I> boolean validate(I input) throws FieldValidationException;

}
