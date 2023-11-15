package com.vn.em.service.impl;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.domain.dto.request.LoginRequestDto;
import com.vn.em.domain.dto.request.TokenRefreshRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.LoginResponseDto;
import com.vn.em.domain.dto.response.TokenRefreshResponseDto;
import com.vn.em.exception.UnauthorizedException;
import com.vn.em.security.UserPrincipal;
import com.vn.em.security.jwt.JwtTokenProvider;
import com.vn.em.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
            String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);
            return new LoginResponseDto(accessToken, refreshToken, userPrincipal.getId(), authentication.getAuthorities());
        } catch (InternalAuthenticationServiceException e) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
        }
    }

    @Override
    public TokenRefreshResponseDto refresh(TokenRefreshRequestDto request) {
        return null;
    }

    @Override
    public CommonResponseDto logout(HttpServletRequest request,
                                    HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return new CommonResponseDto(true, MessageConstant.SUCCESSFULLY_LOGOUT);
    }

}
