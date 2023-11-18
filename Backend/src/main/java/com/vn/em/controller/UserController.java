package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.ChangeAvatarRequestDto;
import com.vn.em.domain.dto.request.ChangePasswordRequestDto;
import com.vn.em.domain.dto.request.UserCreateDto;
import com.vn.em.domain.dto.request.UserUpdateDto;
import com.vn.em.security.CurrentUser;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class UserController {

    private final UserService userService;

    @Tag(name = "user-controller")
    @Operation(summary = "API get user by id")
    @GetMapping(UrlConstant.User.GET_USER)
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return VsResponseUtil.success(userService.getUserById(id));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API get current user login")
    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getCurrentUser(@Parameter(name = "principal", hidden = true)
                                            @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(userService.getCurrentUser(user.getId()));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API get all user")
    @GetMapping(UrlConstant.User.GET_ALL_USER)
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "departmentId", required = false) Integer departmentId,
                                         @RequestParam(name = "enabled", required = false) Boolean enabled,
                                         @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(userService.getAll(departmentId, enabled, paginationFullRequestDto));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API create user")
    @PostMapping(UrlConstant.User.CREATE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        return VsResponseUtil.success(userService.createUser(userCreateDto));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API update user by id")
    @PatchMapping(UrlConstant.User.UPDATE)
    public ResponseEntity<?> updateUserById(@Parameter(name = "principal", hidden = true)
                                            @CurrentUser UserPrincipal user,
                                            @Valid @ModelAttribute UserUpdateDto userUpdateDto) {
        return VsResponseUtil.success(userService.updateUserById(user.getId(), userUpdateDto));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API change avatar")
    @PatchMapping(value = UrlConstant.User.CHANGE_AVATAR)
    public ResponseEntity<?> changeAvatar(@Parameter(name = "principal", hidden = true)
                                          @CurrentUser UserPrincipal user,
                                          @Valid @ModelAttribute ChangeAvatarRequestDto changeAvatarRequestDto) {
        return VsResponseUtil.success(userService.changeAvatar(user.getId(), changeAvatarRequestDto));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API change password")
    @PatchMapping(UrlConstant.User.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@Parameter(name = "principal", hidden = true)
                                            @CurrentUser UserPrincipal user,
                                            @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return VsResponseUtil.success(userService.changePassword(user.getId(), changePasswordRequestDto));
    }

    @Tag(name = "user-controller")
    @Operation(summary = "API delete user by id")
    @DeleteMapping(UrlConstant.User.DELETE)
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
        return VsResponseUtil.success(userService.deleteUserById(id));
    }

}
