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
public class UserRoomDto extends UserDateAuditingDto {
    private Integer userId;
    private String fullName;
    private String nickname;
    private String avatar;
    private Integer roomId;

}
