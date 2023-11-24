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
    })
    SalaryDto mapSalaryToSalaryDto(Salary salary);

    List<SalaryDto> mapSalariesToSalaryDtos(List<Salary> salaries);

}
