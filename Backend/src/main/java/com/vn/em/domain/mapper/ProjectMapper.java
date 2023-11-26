package com.vn.em.domain.mapper;

import com.vn.em.constant.CommonConstant;
import com.vn.em.domain.dto.request.ProjectCreateDto;
import com.vn.em.domain.dto.request.ProjectUpdateDto;
import com.vn.em.domain.dto.response.ProjectDto;
import com.vn.em.domain.entity.Project;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    @Mappings({
            @Mapping(target = "dueDate", source = "dueDate", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "timelineStart", source = "timelineStart", dateFormat = CommonConstant.PATTERN_DATE),
    })
    Project mapProjectCreateDtoToProject(ProjectCreateDto projectCreateDto);


    @Mappings({
            @Mapping(target = "projectManagerId", source = "projectManager.id"),
            @Mapping(target = "statusId", source = "status.id"),
            @Mapping(target = "statusName", source = "status.name"),
    })
    ProjectDto mapProjectToProjectDto(Project project);

    List<ProjectDto> mapProjectsToProjectDtos(List<Project> projects);

    @Mappings({
            @Mapping(target = "dueDate", source = "dueDate", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "timelineStart", source = "timelineStart", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "timelineEnd", source = "timelineEnd", dateFormat = CommonConstant.PATTERN_DATE),
    })
    void update(@MappingTarget Project project, ProjectUpdateDto projectUpdateDto);

}
