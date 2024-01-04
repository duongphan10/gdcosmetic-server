package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.RegexConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordRequestDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = RegexConstant.PASSWORD, message = ErrorMessage.INVALID_FORMAT_PASSWORD)
    private String password;

}
