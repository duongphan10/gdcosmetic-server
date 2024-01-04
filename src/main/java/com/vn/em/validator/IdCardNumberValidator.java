package com.vn.em.validator;


import com.vn.em.validator.annotation.ValidIdCardNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IdCardNumberValidator implements ConstraintValidator<ValidIdCardNumber, String> {
    private Pattern pattern;

    @Override
    public void initialize(ValidIdCardNumber validIdCardNumber) {
        String regex = validIdCardNumber.regexp();
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String idCardNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (idCardNumber == null)
            return true;
        return pattern.matcher(idCardNumber).matches();
    }
}
