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
public class ProjectDto extends UserDateAuditingDto {
    private Integer id;
    private String name;
    private String description;
    private String purpose;
    private String requirement;
    private String stakeholder;
    private Long budget;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate timelineStart;
    private LocalDate timelineEnd;
    private String note;
    private Integer projectManagerId;
    private String projectManagerCode;
    private String projectManagerFullName;
    private String projectManagerDepartmentId;
    private String projectManagerDepartment;
    private Integer statusId;
    private String statusName;

}
