package com.vn.em.validator.annotation;


import com.vn.em.validator.DateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = {DateTimeValidator.class})
public @interface ValidDateTime {

    String message() default "invalid.datetime-format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
