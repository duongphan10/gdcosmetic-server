package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.request.DepartmentRequestDto;
import com.vn.em.domain.dto.response.DepartmentDto;
import com.vn.em.domain.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {
    Department mapDepartmentRequestDtoToDepartment(DepartmentRequestDto departmentRequestDto);

    DepartmentDto mapDepartmentToDepartmentDto(Department department);

    List<DepartmentDto> mapDepartmentsToDepartmentDtos(List<Department> departments);

    void update(@MappingTarget Department department, DepartmentRequestDto departmentRequestDto);
}
