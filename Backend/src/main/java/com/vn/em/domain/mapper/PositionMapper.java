package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.request.PositionCreateDto;
import com.vn.em.domain.dto.request.PositionUpdateDto;
import com.vn.em.domain.dto.response.PositionDto;
import com.vn.em.domain.entity.Position;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PositionMapper {
    Position mapPositionCreateDtoToPosition(PositionCreateDto positionCreateDto);

    @Mappings({
            @Mapping(target = "departmentId", source = "department.id"),
            @Mapping(target = "departmentName", source = "department.name"),
    })
    PositionDto mapPositionToPositionDto(Position position);

    List<PositionDto> mapPositionsToPositionDtos(List<Position> positions);

    void update(@MappingTarget Position position, PositionUpdateDto positionUpdateDto);

}
