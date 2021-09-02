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

    /**
     * Validates a given inputs fields, please not that the input class must have the @Validate annotation
     * @param input The object that has to be validated
     * @param <I> Just some fun Generics
     * @return Returns a ValidationResult, which will indicate if the object is valid or not,

     * if it is invalid a map of errors will be returned as well
     * @throws FieldValidationException Can be thrown
     */
    @Override
    public <I> ValidationResult validate(I input) throws FieldValidationException {
        if (input == null)
            throw new FieldValidationException("Null object provided for validation");

        if (input.getClass().getAnnotation(Validate.class) == null)
            throw new FieldValidationException("invalid object provided for validation");

        try {
            return validateStringFields(input.getClass().getDeclaredFields(), input);
        } catch (IllegalAccessException e) {
            throw new FieldValidationException("Unable to validate object");
        }

    }

    /**
     * This is the method that will actually perform the validation with it's slightly convoluted logic
     * @param fields The declared fields from the input object
     * @param input The input object itself
     * @param <I> The type of the input object
     * @return This returns the actual Validation result
     * @throws IllegalAccessException This might be thrown thanks to some reflection issues
     */
    private <I> ValidationResult validateStringFields(Field[] fields, I input) throws IllegalAccessException {
        Map<String, String> errors = new HashMap<>();
        for (Field field : fields) {
            ValidateString validateString = field.getAnnotation(ValidateString.class);
            if (isValidatableField(validateString, field)) {

                if (isNonNullValueNull(validateString, field, input)) {
                    errors.put(field.getName(), "Null value in a non-nullable field");
                    continue; // the field is null and it is not nullable, no need to continue further validation
                } else if (isNullableValueNull(validateString, field, input))
                    continue; // because the field is nullable and null, it is valid and we don't have to continue further validation on it

                if (validateString.values().length > 0 && !validateUsingValues(field, validateString.values(), input))
                    errors.put(field.getName(), "Value provided does not match required values");

                if (!validateString.pattern().equals("") && !validateUsingPattern(field, validateString.pattern(), validateString.matches(), input))
                    errors.put(field.getName(), "Value provided does not match specified pattern");
            }
        }

        return ValidationResult.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
    }

    /**
     *
     * @param field
     * @param values
     * @param input
     * @param <I>
     * @return
     * @throws IllegalAccessException
     */
    private <I> boolean validateUsingValues(Field field, String[] values, I input) throws IllegalAccessException {
        String inputValue = field.get(input).toString();

        for (String value : values) {
            if (value.equals(inputValue))
                return true;
        }

        return false;
    }

    /**
     *
     * @param field
     * @param inputPattern
     * @param matches
     * @param input
     * @param <I>
     * @return
     * @throws IllegalAccessException
     */
    private <I> boolean validateUsingPattern(Field field, String inputPattern, boolean matches, I input) throws IllegalAccessException {
        String inputValue = field.get(input).toString();

        Pattern pattern = Pattern.compile(inputPattern);
        Matcher matcher = pattern.matcher(inputValue);

        if (matches)
            return matcher.matches();
        else
            return matcher.find();

    }

    /**
     *
     * @param validateString
     * @param field
     * @return
     */
    private boolean isValidatableField(ValidateString validateString, Field field) {
        return field.getType().isAssignableFrom(String.class) && validateString != null;
    }

    /**
     *
     * @param validateString
     * @param field
     * @param input
     * @param <I>
     * @return
     * @throws IllegalAccessException
     */
    private <I> boolean isNonNullValueNull(ValidateString validateString, Field field, I input) throws IllegalAccessException {
        return !validateString.nullable() && field.get(input) == null;
    }

    /**
     *
     * @param validateString
     * @param field
     * @param input
     * @param <I>
     * @return
     * @throws IllegalAccessException
     */
    private <I> boolean isNullableValueNull(ValidateString validateString, Field field, I input) throws IllegalAccessException {
        return validateString.nullable() && field.get(input) == null;
    }

}
