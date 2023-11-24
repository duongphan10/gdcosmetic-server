package com.vn.em.domain.dto.response;

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
public class SalaryDto extends UserDateAuditingDto {
    private Integer id;
    private Long realSalary;
    private Long allowance;
    private Long bonus;
    private Long deduction;
    private Long insurance;
    private Long taxableIncome;
    private Long tax;
    private Long netSalary;
    private Boolean paymentStatus;
    private LocalDateTime paymentDate;
    private Integer attendanceId;

}
