package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.request.RoomCreateDto;
import com.vn.em.domain.dto.request.RoomUpdateDto;
import com.vn.em.domain.dto.response.RoomDto;
import com.vn.em.domain.entity.Room;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {

    Room mapRoomCreateDtoToRoom(RoomCreateDto roomCreateDto);

    RoomDto mapRoomToRoomDto(Room room);

    List<RoomDto> mapRoomsToRoomDtos(List<Room> rooms);

    @Mappings({
            @Mapping(target = "roomAvatar", ignore = true),
    })
    void update(@MappingTarget Room room, RoomUpdateDto roomUpdateDto);

}
