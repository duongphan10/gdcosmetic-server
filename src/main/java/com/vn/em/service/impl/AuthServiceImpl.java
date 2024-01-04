package com.vn.em.service.impl;

import com.vn.em.constant.CommonConstant;
import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.domain.dto.common.DataMailDto;
import com.vn.em.domain.dto.request.ForgotPasswordRequestDto;
import com.vn.em.domain.dto.request.LoginRequestDto;
import com.vn.em.domain.dto.request.TokenRefreshRequestDto;
import com.vn.em.domain.dto.request.VerifyRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.LoginResponseDto;
import com.vn.em.domain.dto.response.TokenRefreshResponseDto;
import com.vn.em.domain.entity.Code;
import com.vn.em.domain.entity.User;
import com.vn.em.exception.NotFoundException;
import com.vn.em.exception.UnauthorizedException;
import com.vn.em.repository.CodeRepository;
import com.vn.em.repository.UserRepository;
import com.vn.em.security.UserPrincipal;
import com.vn.em.security.jwt.JwtTokenProvider;
import com.vn.em.service.AuthService;
import com.vn.em.util.CodeUtil;
import com.vn.em.util.SendMailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final SendMailUtil sendMailUtil;

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

    @Override
    public CommonResponseDto forgotPassword(ForgotPasswordRequestDto requestDto) {
        User user = userRepository.findUserByEmail(requestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_WITH_EMAIL, new String[]{requestDto.getEmail()}));

        String verificationCode = CodeUtil.generateCode(CommonConstant.VERIFICATION_C0DE_LENGTH);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(CommonConstant.VERIFICATION_CODE_EXPIRATION_MINUTES);

        Code code = codeRepository.findByUserId(user.getId());
        if (code != null) {
            code.setVerificationCode(verificationCode);
            code.setExpirationTime(expirationTime);
        } else {
            code = new Code(null, verificationCode, expirationTime, true, user);
        }
        codeRepository.save(code);

        Map<String, Object> props = new HashMap<>();
        props.put("fullName", user.getEmployee().getFullName());
        props.put("expirationTime", CommonConstant.VERIFICATION_CODE_EXPIRATION_MINUTES);
        props.put("code", verificationCode);

        DataMailDto mail = new DataMailDto(user.getEmployee().getEmail(),
                MessageConstant.SUBJECT_MAIL_RESET_PASSWORD, null, props);
        try {
            sendMailUtil.sendEmailWithHTML(mail, "verify-forgot-password.html");
        } catch (Exception e) {
            log.error("Send mail failed for {}", e.getMessage());
        }
        return new CommonResponseDto(true, MessageConstant.VERIFY_FORGOT_PASSWORD + user.getEmployee().getEmail());
    }

    @Override
    public CommonResponseDto verifyForgotPassword(VerifyRequestDto verifyRequestDto) {
        User user = userRepository.findUserByEmail(verifyRequestDto.getEmail()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_WITH_EMAIL, new String[]{verifyRequestDto.getEmail()}));
        Code code = codeRepository.findByUserId(user.getId());
        if (code != null) {
            if (!code.getVerificationCode().equals(verifyRequestDto.getVerificationCode()))
                return new CommonResponseDto(false, MessageConstant.VERIFY_FORGOT_PASSWORD_INVALID);
            if (!code.getExpirationTime().isAfter(LocalDateTime.now()) && !code.getValid())
                return new CommonResponseDto(false, MessageConstant.VERIFY_FORGOT_PASSWORD_EXPIRED);

            code.setValid(false);
            codeRepository.save(code);

            UserPrincipal userPrincipal = UserPrincipal.create(user);
            String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
            return new CommonResponseDto(true, accessToken);
        }
        return new CommonResponseDto(false, MessageConstant.ERR_VERIFY_FORGOT_PASSWORD);
    }

}
