package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.MessageRequestDto;
import com.vn.em.domain.dto.response.MessageDto;

public interface MessageService {

    PaginationResponseDto<MessageDto> getAllByRoomId(Integer roomId, PaginationFullRequestDto paginationFullRequestDto);

    MessageDto sendMessage(MessageRequestDto messageRequestDto);

}
