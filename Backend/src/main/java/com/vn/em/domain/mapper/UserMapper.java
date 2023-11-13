package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.request.UserCreateDto;
import com.vn.em.domain.dto.response.UserDto;
import com.vn.em.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateDto userCreateDTO);

    @Mappings({
            @Mapping(target = "roleName", source = "role.name"),
    })
    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> user);

}
