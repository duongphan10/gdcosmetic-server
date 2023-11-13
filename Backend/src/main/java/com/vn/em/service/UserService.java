package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.response.UserDto;
import com.vn.em.security.UserPrincipal;

public interface UserService {

    UserDto getUserById(String userId);

    PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request);

    UserDto getCurrentUser(UserPrincipal principal);

}
