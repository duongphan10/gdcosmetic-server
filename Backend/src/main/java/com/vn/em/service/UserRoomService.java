package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.UserRoomRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.UserRoomDto;

public interface UserRoomService {

    PaginationResponseDto<UserRoomDto> getAllByRoomId(Integer roomId, PaginationFullRequestDto paginationFullRequestDto);

    UserRoomDto create(UserRoomRequestDto roomRequestDto);

    UserRoomDto changeNickname(UserRoomRequestDto roomUpdateDto);

    CommonResponseDto delete(Integer userId, UserRoomRequestDto userRoomRequestDto);

}
