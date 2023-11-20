package com.vn.em.domain.dto.response;

import com.vn.em.domain.dto.common.UserDateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalaryAdjustmentDto extends UserDateAuditingDto {
    private Integer id;
    private Long newSalary;
    private String reason;
    private String rejectionReason;
    private Integer employeeId;
    private Integer statusId;
    private String statusName;
}
