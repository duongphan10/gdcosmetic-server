package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.validator.annotation.ValidDate;
import com.vn.em.validator.annotation.ValidFileImage;
import com.vn.em.validator.annotation.ValidIdCardNumber;
import com.vn.em.validator.annotation.ValidPhone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeUpdateDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String fullName;
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Boolean gender;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @ValidDate
    private String birthday;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String hometown;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String ethnicity;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String religion;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String nationality;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String address;
    @ValidFileImage
    private MultipartFile image;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @ValidIdCardNumber
    private String idCardNumber;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @ValidDate
    private String idCardIssuedDate;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String idCardIssuedLocation;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @ValidPhone
    private String phoneNumber;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Email(message = ErrorMessage.INVALID_FORMAT_EMAIL)
    private String email;
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Positive(message = ErrorMessage.INVALID_NUMBER_POSITIVE)
    private Long salary;
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Positive(message = ErrorMessage.INVALID_NUMBER_POSITIVE)
    private Integer positionId;
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Positive(message = ErrorMessage.INVALID_NUMBER_POSITIVE)
    private Integer statusId;
}
