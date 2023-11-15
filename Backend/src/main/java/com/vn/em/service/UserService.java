package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.ChangePasswordRequestDto;
import com.vn.em.domain.dto.request.UserCreateDto;
import com.vn.em.domain.dto.request.UserUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDto getUserById(Integer id);

    UserDto getCurrentUser(Integer id);

    PaginationResponseDto<UserDto> getAllUsers(PaginationFullRequestDto request);

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto updateUserById(Integer id, UserUpdateDto userUpdateDto);

    UserDto changeAvatar(Integer id, MultipartFile avatar);

    CommonResponseDto changePassword(Integer id, ChangePasswordRequestDto changePasswordRequestDto);

    CommonResponseDto deleteUserById(Integer id);

}
