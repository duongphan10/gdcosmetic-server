package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.response.SalaryDto;
import com.vn.em.domain.entity.Salary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SalaryMapper {

    @Mappings({
            @Mapping(target = "attendanceId", source = "attendance.id"),
            @Mapping(target = "year", source = "attendance.year"),
            @Mapping(target = "month", source = "attendance.month"),
            @Mapping(target = "employeeCode", source = "attendance.employee.employeeCode"),
            @Mapping(target = "fullName", source = "attendance.employee.fullName"),
            @Mapping(target = "departmentName", source = "attendance.employee.position.department.name"),
            @Mapping(target = "salary", source = "attendance.employee.salary"),
    })
    SalaryDto mapSalaryToSalaryDto(Salary salary);

    List<SalaryDto> mapSalariesToSalaryDtos(List<Salary> salaries);

}
