package com.vn.em.validator.annotation;


import com.vn.em.validator.IdCardNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = {IdCardNumberValidator.class})
public @interface ValidIdCardNumber {

    String message() default "invalid.id.card.number-format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default "^\\d{12}$";
}
