package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.validator.annotation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectCreateDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String description;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String purpose;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String requirement;
    private String stakeholder;
    private Long budget;
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @ValidDate
    private String dueDate;
    @ValidDate
    private String timelineStart;
    private String note;
    private Integer projectManagerId;

}
