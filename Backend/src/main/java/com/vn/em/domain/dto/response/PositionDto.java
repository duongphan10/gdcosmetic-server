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
public class PositionDto extends UserDateAuditingDto {
    private Integer id;
    private String name;
    private String description;
    private Integer departmentId;
    private String departmentName;

}
