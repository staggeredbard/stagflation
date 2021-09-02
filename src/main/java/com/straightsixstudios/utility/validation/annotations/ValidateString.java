package com.straightsixstudios.utility.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author charles
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateString {

    String[] values() default "";
    String pattern() default "";
    boolean matches() default false;
    boolean nullable() default true;

}
