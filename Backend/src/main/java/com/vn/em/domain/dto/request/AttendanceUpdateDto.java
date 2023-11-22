package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceUpdateDto {
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Float actualWorkingDays;
    @PositiveOrZero(message = ErrorMessage.INVALID_NUMBER_POSITIVE_OR_ZERO)
    private Integer lateArrival;

}
