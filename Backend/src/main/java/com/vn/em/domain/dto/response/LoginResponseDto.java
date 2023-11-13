package com.vn.em.domain.dto.response;

import com.vn.em.constant.CommonConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@NoArgsConstructor
@Setter
@Getter
public class LoginResponseDto {

    private String tokenType = CommonConstant.BEARER_TOKEN;

    private String accessToken;

    private String refreshToken;

    private Integer id;

    private Collection<? extends GrantedAuthority> authorities;

    public LoginResponseDto(String accessToken, String refreshToken, Integer id, Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.authorities = authorities;
    }

}
