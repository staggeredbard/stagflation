package com.straightsixstudios.utility.validation;

import com.straightsixstudios.utility.validation.annotations.Validate;
import com.straightsixstudios.utility.validation.annotations.ValidateString;
import com.straightsixstudios.utility.validation.exception.FieldValidationException;
import com.straightsixstudios.utility.validation.output.ValidationResult;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author charles
 */
public class FieldValidator implements Validator {

    @Override
    public <I> ValidationResult validate(I input) throws FieldValidationException {
        if(input == null)
            throw new FieldValidationException("Null object provided for validation");

        if(input.getClass().getAnnotation(Validate.class) == null)
            throw new FieldValidationException("invalid object provided for validation");

        try {
            return validateStringFields(input.getClass().getDeclaredFields(), input);
        } catch (IllegalAccessException e) {
            throw  new FieldValidationException("Unable to validate object");
        }

    }

    private <I> ValidationResult validateStringFields(Field[] fields, I input) throws IllegalAccessException {
        Map<String, String> errors = new HashMap<>();
        for(Field field : fields) {
            ValidateString validateString = field.getAnnotation(ValidateString.class);
            field.setAccessible(true);
            if(field.getType().isAssignableFrom(String.class) && validateString != null) {

                if(!validateString.nullable() && field.get(input) == null) {
                    errors.put(field.getName(), "Null value in a non-nullable field");
                    continue; // the field is null and it is not nullable, no need to continue further validation
                } else if (validateString.nullable() && field.get(input) == null)
                    continue; // because the field is nullable and null, it is valid and we don't have to continue further validation on it

                if(validateString.values().length > 0 && !validateUsingValues(field, validateString.values(), input))
                    errors.put(field.getName(), "Value provided does not match required values");

                if(!validateString.pattern().equals("") && !validateUsingPattern(field, validateString.pattern(), validateString.matches(), input))
                    errors.put(field.getName(), "Value provided does not match specified pattern");
            }
        }

        return ValidationResult.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
    }

    private <I> boolean validateUsingValues(Field field, String[] values, I input) throws IllegalAccessException {
        String inputValue = field.get(input).toString();

        for(String value : values) {
            if(value.equals(inputValue))
                return true;
        }

        return false;
    }

    private <I> boolean validateUsingPattern(Field field, String inputPattern, boolean matches, I input) throws IllegalAccessException {
        String inputValue = field.get(input).toString();

        Pattern pattern = Pattern.compile(inputPattern);
        Matcher matcher = pattern.matcher(inputValue);

        if(matches)
            return matcher.matches();
        else
            return matcher.find();

    }

}
