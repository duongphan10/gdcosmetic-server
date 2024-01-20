package com.vn.em.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vn.em.constant.CommonConstant;
import com.vn.em.domain.dto.common.UserDateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecognitionDto extends UserDateAuditingDto {
    private Integer id;
    private Boolean type;
    private String reason;
    private Long amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstant.PATTERN_DATE_TIME)
    private LocalDateTime date;
    private String rejectionReason;
    private Integer employeeId;
    private String employeeCode;
    private String fullName;
    private String departmentName;
    private Integer statusId;
    private String statusName;

}
