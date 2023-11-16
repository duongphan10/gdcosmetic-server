package com.vn.em.domain.dto.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class UserDateAuditingDto extends DateAuditingDto {

    private Integer createdBy;

    private Integer lastModifiedBy;

}
