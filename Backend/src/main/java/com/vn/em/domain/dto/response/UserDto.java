package com.vn.em.domain.dto.response;

import com.vn.em.domain.dto.common.DateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto extends DateAuditingDto {
    private Integer id;
    private String username;
    private String avatar;
    private String employeeId;
    private String employeeCode;
    private String fullName;
    private Boolean gender;
    private LocalDate birthday;
    private String email;
    private String departmentName;
    private String positionName;
    private String employeeStatusName;
    private String roleName;
    private boolean enabled;

}

