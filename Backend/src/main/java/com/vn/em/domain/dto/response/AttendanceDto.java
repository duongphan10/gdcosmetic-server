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
public class AttendanceDto extends UserDateAuditingDto {
    private Integer id;
    private Integer year;
    private Integer month;
    private Float actualWorkingDays;
    private Integer lateArrival;
    private Float workingDaysOfMonth;
    private String note;
    private Integer employeeId;

}
