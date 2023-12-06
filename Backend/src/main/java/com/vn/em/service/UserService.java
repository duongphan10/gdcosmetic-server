package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.*;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.UserDto;

public interface UserService {
    UserDto getUserById(Integer id);

    UserDto getCurrentUser(Integer id);

    PaginationResponseDto<UserDto> getAll(Integer departmentId, Boolean enabled, PaginationFullRequestDto request);

    PaginationResponseDto<UserDto> searchOtherUser(Integer roomId, PaginationFullRequestDto request);

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto updateUserById(Integer id, UserUpdateDto userUpdateDto);

    UserDto changeAvatar(Integer id, ChangeAvatarRequestDto changeAvatarRequestDto);

    CommonResponseDto changePassword(Integer id, ChangePasswordRequestDto changePasswordRequestDto);

    CommonResponseDto createNewPassword(Integer id, NewPasswordRequestDto newPasswordRequestDto);

    CommonResponseDto deleteUserById(Integer id);

}
