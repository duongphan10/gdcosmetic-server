package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.response.FileDto;
import com.vn.em.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FileMapper {

    FileDto mapFileToFileDto(File file);

    List<FileDto> mapFilesToFileDtos(List<File> files);

}
