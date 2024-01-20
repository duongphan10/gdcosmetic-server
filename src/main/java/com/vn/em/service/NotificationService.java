package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.entity.User;

import java.util.List;

public interface NotificationService {

    NotificationDto create(Integer type, String message, User user, Integer fromId);

    NotificationDto create(Integer type, String message, Integer userId, Integer fromId);

    PaginationResponseDto<NotificationDto> getAllMyNotification(Integer userId, PaginationFullRequestDto paginationFullRequestDto);

    List<NotificationDto> getUnreadNotification(Integer userId);

    CommonResponseDto seenAllNotification(Integer userId);

}
