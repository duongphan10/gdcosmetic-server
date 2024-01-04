package com.vn.em.domain.mapper;

import com.vn.em.constant.CommonConstant;
import com.vn.em.domain.dto.request.RecognitionCreateDto;
import com.vn.em.domain.dto.request.RecognitionUpdateDto;
import com.vn.em.domain.dto.response.RecognitionDto;
import com.vn.em.domain.entity.Recognition;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecognitionMapper {

    Recognition mapRecognitionCreateDtoToRecognition(RecognitionCreateDto recognitionCreateDto);

    @Mappings({
            @Mapping(target = "employeeId", source = "employee.id"),
            @Mapping(target = "employeeCode", source = "employee.employeeCode"),
            @Mapping(target = "fullName", source = "employee.fullName"),
            @Mapping(target = "departmentName", source = "employee.position.department.name"),
            @Mapping(target = "statusId", source = "status.id"),
            @Mapping(target = "statusName", source = "status.name"),
    })
    RecognitionDto mapRecognitionToRecognitionDto(Recognition recognition);

    List<RecognitionDto> mapRecognitionsToRecognitionDtos(List<Recognition> recognitions);

    void update(@MappingTarget Recognition recognition, RecognitionUpdateDto recognitionUpdateDto);

}
