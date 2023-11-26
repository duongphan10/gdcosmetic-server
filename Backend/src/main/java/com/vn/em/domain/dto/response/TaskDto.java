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
public class TaskDto extends UserDateAuditingDto {
    private Integer id;
    private String name;
    private String description;
    private String requirement;
    private Long budget;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private String note;
    private Integer projectId;
    private String projectName;
    private Integer employeeId;
    private Integer statusId;
    private String statusName;

}
