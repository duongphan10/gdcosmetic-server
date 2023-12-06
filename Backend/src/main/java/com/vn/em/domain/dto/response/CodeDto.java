package com.vn.em.domain.dto.response;

import com.vn.em.domain.dto.common.DateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CodeDto extends DateAuditingDto {
    private String id;
    private String verificationCode;
    private LocalDateTime expirationTime;
    private Boolean valid;
    private String email;
    private String userId;

}
