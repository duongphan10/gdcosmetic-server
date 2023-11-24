package com.vn.em.domain.dto.response;

import com.vn.em.domain.dto.common.UserDateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecognitionDto extends UserDateAuditingDto {
    private Integer id;
    private Boolean type;
    private String reason;
    private String description;
    private Long amount;
    private LocalDate date;
    private String rejectionReason;
    private Integer employeeId;
    private Integer statusId;
    private String statusName;

}
