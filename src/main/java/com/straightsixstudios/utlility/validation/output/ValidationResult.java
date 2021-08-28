package com.straightsixstudios.utlility.validation.output;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

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
    private List<String> errors;

}
