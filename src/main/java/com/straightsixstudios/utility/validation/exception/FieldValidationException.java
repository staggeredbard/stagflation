package com.straightsixstudios.utility.validation.exception;
/**
 * @author charles
 */
public class FieldValidationException extends Exception {
    public FieldValidationException() {
    }

    public FieldValidationException(String message) {
        super(message);
    }

    public FieldValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
