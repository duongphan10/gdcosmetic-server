package com.vn.em.domain.mapper;

import com.vn.em.constant.CommonConstant;
import com.vn.em.domain.dto.response.FileDto;
import com.vn.em.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FileMapper {
    @Mappings({
            @Mapping(target = "createdDate", source = "createdDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
            @Mapping(target = "lastModifiedDate", source = "lastModifiedDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
    })
    FileDto mapFileToFileDto(File file);

    List<FileDto> mapFilesToFileDtos(List<File> files);

}
