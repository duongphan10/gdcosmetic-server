package com.vn.em.service.impl;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.constant.SortByDataConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.entity.Notification;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.mapper.NotificationMapper;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.NotificationRepository;
import com.vn.em.repository.UserRepository;
import com.vn.em.service.NotificationService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationDto create(Integer type, String message, User user, Integer fromId) {
        User from = null;
        if (fromId != null) {
            from = userRepository.findById(fromId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{fromId.toString()}));
        }
        Notification notification = new Notification(null, type, message, false, user, from);
        return notificationMapper.mapNotificationToNotificationDto(notificationRepository.save(notification));
    }

    @Override
    public NotificationDto create(Integer type, String message, Integer userId, Integer fromId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        User from = null;
        if (fromId != null) {
            from = userRepository.findById(fromId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{fromId.toString()}));
        }
        Notification notification = new Notification(null, type, message, false, user, from);
        return notificationMapper.mapNotificationToNotificationDto(notificationRepository.save(notification));
    }

    @Override
    public PaginationResponseDto<NotificationDto> getAllMyNotification(Integer userId, PaginationFullRequestDto paginationFullRequestDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.NOTIFICATION);
        Page<Notification> notificationPage = notificationRepository.getAllByUserId(userId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.NOTIFICATION, notificationPage);

        List<NotificationDto> notificationDtos = notificationMapper.mapNotificationsToNotificationDtos(notificationPage.getContent());
        return new PaginationResponseDto<>(meta, notificationDtos);
    }

    @Override
    public List<NotificationDto> getUnreadNotification(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        List<Notification> notifications = notificationRepository.getUnreadByUserId(userId);
        return notificationMapper.mapNotificationsToNotificationDtos(notifications);
    }

    @Override
    public CommonResponseDto seenAllNotification(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        notificationRepository.seenAllByUserId(userId);
        return new CommonResponseDto(true, MessageConstant.SEEN_ALL_NOTIFICATION);
    }

}
