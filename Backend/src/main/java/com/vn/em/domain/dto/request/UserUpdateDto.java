package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.RegexConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = RegexConstant.USERNAME, message = ErrorMessage.INVALID_FORMAT_USERNAME)
    private String username;
    private boolean enable;
}
