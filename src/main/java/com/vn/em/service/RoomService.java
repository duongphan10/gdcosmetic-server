package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.RoomCreateDto;
import com.vn.em.domain.dto.request.RoomUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.RoomDto;

public interface RoomService {
    RoomDto getById(Integer id);

    PaginationResponseDto<RoomDto> getAllMyConversation(Integer userId, PaginationFullRequestDto paginationFullRequestDto);

    RoomDto create(Integer userId, RoomCreateDto roomCreateDto);

    RoomDto updateById(Integer id, RoomUpdateDto roomUpdateDto);

    CommonResponseDto deleteById(Integer id, Integer userId);

}
