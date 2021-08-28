package com.straightsixstudios.utlility.validation;

import com.straightsixstudios.utlility.validation.annotations.Validate;
import com.straightsixstudios.utlility.validation.exception.FieldValidationException;

import java.lang.reflect.AnnotatedType;

/**
 * @author charles
 */
public class FieldValidator implements Validator {

    @Override
    public <I> boolean validate(I input) throws FieldValidationException {
        if(input == null)
            throw new FieldValidationException("Null object provided for validation");

        if(!isValidatable(input.getClass().getAnnotatedInterfaces()))
            throw new FieldValidationException("invalid object provided for validation");

        return false;
    }

    private boolean isValidatable(AnnotatedType[] annotatedTypes) {
        for(AnnotatedType annotatedType : annotatedTypes) {
            if(annotatedType instanceof Validate) {
                return true;
            }
        }
        return false;
    }

}
