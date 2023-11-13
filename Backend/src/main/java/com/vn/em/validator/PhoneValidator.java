package com.vn.em.validator;


import com.vn.em.validator.annotation.ValidPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    private Pattern pattern;

    @Override
    public void initialize(ValidPhone validPhone) {
        String regex = validPhone.regexp();
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (phone == null) return true;
        return pattern.matcher(phone).matches();
    }
}
