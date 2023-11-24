package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalaryRequestDto {
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Min(value = 2010, message = ErrorMessage.INVALID_YEAR)
    private Integer year;
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Min(value = 1, message = ErrorMessage.INVALID_MONTH)
    @Max(value = 12, message = ErrorMessage.INVALID_MONTH)
    private Integer month;

}
