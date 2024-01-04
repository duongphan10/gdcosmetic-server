package com.vn.em.validator;


import com.vn.em.constant.CommonConstant;
import com.vn.em.validator.annotation.ValidFileImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FileImageValidator implements ConstraintValidator<ValidFileImage, MultipartFile> {

    private String fileSizeExceededMessage;

    @Override
    public void initialize(ValidFileImage constraintAnnotation) {
        this.fileSizeExceededMessage = constraintAnnotation.fileSizeExceededMessage();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (file == null)
            return true;
        String contentType = file.getContentType();
        if (isSupportedContentType(Objects.requireNonNull(contentType))) {
            if (file.getSize() > CommonConstant.MAX_IMAGE_SIZE_BYTES) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(fileSizeExceededMessage)
                        .addConstraintViolation();
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isSupportedContentType(String contentType) {
        return CommonConstant.CONTENT_TYPE_IMAGE.contains(contentType.substring("image/".length()));
    }

}
