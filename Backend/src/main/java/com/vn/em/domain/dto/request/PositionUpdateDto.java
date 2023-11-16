package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PositionUpdateDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;
    private String description;

}
