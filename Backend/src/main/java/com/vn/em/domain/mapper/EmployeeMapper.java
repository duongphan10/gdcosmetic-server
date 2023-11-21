package com.vn.em.domain.mapper;

import com.vn.em.constant.CommonConstant;
import com.vn.em.domain.dto.request.EmployeeCreateDto;
import com.vn.em.domain.dto.request.EmployeeUpdateDto;
import com.vn.em.domain.dto.response.EmployeeDto;
import com.vn.em.domain.entity.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    @Mappings({
            @Mapping(target = "employeeCode", ignore = true),
            @Mapping(target = "birthday", source = "birthday", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "image", ignore = true)
    })
    Employee mapEmployeeCreateDtoToEmployee(EmployeeCreateDto employeeCreateDto);


    @Mappings({
            @Mapping(target = "departmentId", source = "position.department.id"),
            @Mapping(target = "departmentName", source = "position.department.name"),
            @Mapping(target = "positionId", source = "position.id"),
            @Mapping(target = "positionName", source = "position.name"),
            @Mapping(target = "statusId", source = "status.id"),
            @Mapping(target = "statusName", source = "status.name"),
            @Mapping(target = "userId", source = "user.id"),
    })
    EmployeeDto mapEmployeeToEmployeeDto(Employee employee);

    List<EmployeeDto> mapEmployeesToEmployeeDtos(List<Employee> employees);

    @Mappings({
            @Mapping(target = "employeeCode", ignore = true),
            @Mapping(target = "birthday", source = "birthday", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "image", ignore = true)
    })
    void update(@MappingTarget Employee employee, EmployeeUpdateDto employeeUpdateDto);

}
