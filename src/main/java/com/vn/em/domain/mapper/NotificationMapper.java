package com.vn.em.domain.mapper;

import com.vn.em.constant.CommonConstant;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationMapper {
    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "fromId", source = "from.id"),
            @Mapping(target = "fromFullName", source = "from.employee.fullName"),
            @Mapping(target = "fromAvatar", source = "from.avatar"),
            @Mapping(target = "createdDate", source = "createdDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
            @Mapping(target = "lastModifiedDate", source = "lastModifiedDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
    })
    NotificationDto mapNotificationToNotificationDto(Notification notification);

    List<NotificationDto> mapNotificationsToNotificationDtos(List<Notification> notifications);

}
