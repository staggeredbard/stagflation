package com.straightsixstudios.utility.validation.output;

import java.util.Map;
import java.util.Objects;

/**
 * @author charles
 *
 * The return object after validtion to indicate if the object is valid or not, if validation
 *
 */
public class ValidationResult {

    private boolean valid;
    private Map<String, String> errors;

    private ValidationResult(Builder builder) {
        this.valid = builder.valid;
        this.errors = builder.errors;
    }

    public static class Builder {

        private boolean valid;
        private Map<String, String> errors;

        public Builder setValid(boolean valid) {
            this.valid = valid;
            return this;
        }

        public Builder setErrors(Map<String, String> errors) {
            this.errors = errors;
            return this;
        }

        public ValidationResult build() {
            return new ValidationResult(this);
        }
    }

    public boolean isValid() {
        return valid;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ValidationResult that = (ValidationResult) o;
        return valid == that.valid && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valid, errors);
    }
}
