package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.request.SalaryAdjustmentCreateDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentUpdateDto;
import com.vn.em.domain.dto.response.SalaryAdjustmentDto;
import com.vn.em.domain.entity.SalaryAdjustment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SalaryAdjustmentMapper {
    SalaryAdjustment mapSalaryAdjustmentCreateDtoToSalaryAdjustment(SalaryAdjustmentCreateDto salaryAdjustmentCreateDto);


    @Mappings({
            @Mapping(target = "employeeId", source = "employee.id"),
            @Mapping(target = "statusId", source = "status.id"),
            @Mapping(target = "statusName", source = "status.name"),
    })
    SalaryAdjustmentDto mapSalaryAdjustmentToSalaryAdjustmentDto(SalaryAdjustment salaryAdjustment);

    List<SalaryAdjustmentDto> mapSalaryAdjustmentsToSalaryAdjustmentDtos(List<SalaryAdjustment> salaryAdjustments);

    void update(@MappingTarget SalaryAdjustment salaryAdjustment, SalaryAdjustmentUpdateDto salaryAdjustmentUpdateDto);
}
