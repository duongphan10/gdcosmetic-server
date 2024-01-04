package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.entity.User;

public interface NotificationService {

    NotificationDto create(Integer type, String message, User user);

    NotificationDto create(Integer type, String message, Integer userId);

    PaginationResponseDto<NotificationDto> getAllMyNotification(Integer userId, PaginationFullRequestDto paginationFullRequestDto);

    int countUnreadNotification(Integer userId);

    CommonResponseDto seenAllNotification(Integer userId);

}
