package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.security.CurrentUser;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class UserController {

    private final UserService userService;

    @Tag(name = "user-controller")
    @Operation(summary = "API get user")
    @GetMapping(UrlConstant.User.GET_USER)
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return VsResponseUtil.success(userService.getUserById(userId));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API get current user login")
    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getCurrentUser(@Parameter(name = "principal", hidden = true)
                                            @CurrentUser UserPrincipal principal) {
        return VsResponseUtil.success(userService.getCurrentUser(principal));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API get all customer")
    @GetMapping(UrlConstant.User.GET_USERS)
    public ResponseEntity<?> getCustomers(@Valid @ParameterObject PaginationFullRequestDto requestDTO) {
        return VsResponseUtil.success(userService.getCustomers(requestDTO));
    }

}
