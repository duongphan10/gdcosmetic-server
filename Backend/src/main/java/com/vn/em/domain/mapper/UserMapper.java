package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.request.UserCreateDto;
import com.vn.em.domain.dto.request.UserUpdateDto;
import com.vn.em.domain.dto.response.UserDto;
import com.vn.em.domain.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mappings({
            @Mapping(target = "avatar", ignore = true)
    })
    User mapUserCreateToUser(UserCreateDto userCreateDTO);

    @Mappings({
            @Mapping(target = "roleName", source = "role.name"),
    })
    UserDto mapUserToUserDto(User user);

    List<UserDto> mapUsersToUserDtos(List<User> user);

    @Mappings({
            @Mapping(target = "avatar", ignore = true)
    })
    void updateUser(@MappingTarget User user, UserUpdateDto userUpdateDto);

}
