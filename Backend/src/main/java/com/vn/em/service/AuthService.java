package com.vn.em.service;

import com.vn.em.domain.dto.request.LoginRequestDto;
import com.vn.em.domain.dto.request.TokenRefreshRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.LoginResponseDto;
import com.vn.em.domain.dto.response.TokenRefreshResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);

    TokenRefreshResponseDto refresh(TokenRefreshRequestDto request);

    CommonResponseDto logout(HttpServletRequest request);

}
