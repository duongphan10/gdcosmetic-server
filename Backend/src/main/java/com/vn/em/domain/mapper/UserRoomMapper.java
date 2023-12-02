package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.request.UserRoomRequestDto;
import com.vn.em.domain.dto.response.UserRoomDto;
import com.vn.em.domain.entity.UserRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserRoomMapper {

    UserRoom mapUserRoomRequestDtoToUserRoom(UserRoomRequestDto userRoomRequestDto);

    @Mappings({
            @Mapping(target = "roomId", source = "room.id"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "fullName", source = "user.employee.fullName"),
            @Mapping(target = "nickname", source = "nickname"),
            @Mapping(target = "avatar", source = "user.avatar"),
    })
    UserRoomDto mapUserRoomToUserRoomDto(UserRoom userRoom);

    List<UserRoomDto> mapUserRoomsToUserRoomDtos(List<UserRoom> userRooms);

}
