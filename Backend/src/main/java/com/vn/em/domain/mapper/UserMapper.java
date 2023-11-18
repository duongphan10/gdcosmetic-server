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
            @Mapping(target = "employeeId", source = "employee.id"),
            @Mapping(target = "employeeCode", source = "employee.employeeCode"),
            @Mapping(target = "fullName", source = "employee.fullName"),
            @Mapping(target = "gender", source = "employee.gender"),
            @Mapping(target = "birthday", source = "employee.birthday"),
            @Mapping(target = "email", source = "employee.email"),
            @Mapping(target = "departmentName", source = "employee.position.department.name"),
            @Mapping(target = "positionName", source = "employee.position.name"),
            @Mapping(target = "employeeStatusName", source = "employee.status.name"),
            @Mapping(target = "roleName", source = "role.name"),
            @Mapping(target = "enabled", source = "enabled"),
    })
    UserDto mapUserToUserDto(User user);

    List<UserDto> mapUsersToUserDtos(List<User> user);

    @Mappings({
            @Mapping(target = "avatar", ignore = true)
    })
    void updateUser(@MappingTarget User user, UserUpdateDto userUpdateDto);

}
