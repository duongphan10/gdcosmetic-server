package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.response.CodeDto;
import com.vn.em.domain.entity.Code;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CodeMapper {
    @Mappings({
            @Mapping(target = "expirationTime", source = "expirationTime"),
            @Mapping(target = "email", source = "user.employee.email"),
            @Mapping(target = "userId", source = "user.id"),
    })
    CodeDto codeToCodeResponseDto(Code code);

}
