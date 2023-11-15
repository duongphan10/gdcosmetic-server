package com.vn.em.domain.dto.response;

import com.vn.em.domain.dto.common.DateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto extends DateAuditingDto {
    private Integer id;
    private String username;
    private String avatar;
    private String roleName;

}

