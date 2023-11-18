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
public class EmployeeDto extends UserDateAuditingDto {
    private Integer id;
    private String employeeCode;
    private String fullName;
    private Boolean gender;
    private LocalDate birthday;
    private String hometown;
    private String ethnicity;
    private String religion;
    private String nationality;
    private String address;
    private String image;
    private String idCardNumber;
    private LocalDate idCardIssuedDate;
    private String idCardIssuedLocation;
    private String phoneNumber;
    private String email;
    private Long salary;
    private Integer departmentId;
    private String departmentName;
    private Integer positionId;
    private String positionName;
    private Integer statusId;
    private String statusName;
    private Integer userId;
}
