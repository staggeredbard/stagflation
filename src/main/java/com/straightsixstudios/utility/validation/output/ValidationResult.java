package com.straightsixstudios.utility.validation.output;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * @author charles
 *
 * The return object after validtion to indicate if the object is valid or not, if validation
 *
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class ValidationResult {

    private boolean valid;
    private Map<String, String> errors;

}
