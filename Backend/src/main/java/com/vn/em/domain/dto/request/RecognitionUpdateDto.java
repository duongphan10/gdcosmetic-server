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
public class RecognitionUpdateDto {
    private String rejectionReason;
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Min(value = 7, message = ErrorMessage.INVALID_STATUS_UPDATE_RECOGNITION)
    @Max(value = 8, message = ErrorMessage.INVALID_STATUS_UPDATE_RECOGNITION)
    private Integer statusId;
}
