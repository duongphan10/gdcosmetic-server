package com.vn.em.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDto {
    private Integer id;
    private Integer userId;
    private Integer type;
    private String message;
    private Boolean seen;
    private String createdDate;
    private String lastModifiedDate;
    private Integer createdBy;
    private Integer lastModifiedBy;

}
