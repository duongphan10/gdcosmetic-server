package com.vn.em.domain.mapper;

import com.vn.em.constant.CommonConstant;
import com.vn.em.domain.dto.request.TaskCreateDto;
import com.vn.em.domain.dto.request.TaskUpdateDto;
import com.vn.em.domain.dto.response.TaskDto;
import com.vn.em.domain.entity.Task;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    @Mappings({
            @Mapping(target = "startDate", source = "startDate", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "dueDate", source = "dueDate", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "actualStartDate", source = "actualStartDate", dateFormat = CommonConstant.PATTERN_DATE),
    })
    Task mapTaskCreateDtoToTask(TaskCreateDto taskCreateDto);


    @Mappings({
            @Mapping(target = "projectId", source = "project.id"),
            @Mapping(target = "projectName", source = "project.name"),
            @Mapping(target = "projectManager", source = "project.projectManager.fullName"),
            @Mapping(target = "employeeId", source = "employee.id"),
            @Mapping(target = "employeeCode", source = "employee.employeeCode"),
            @Mapping(target = "employeeFullName", source = "employee.fullName"),
            @Mapping(target = "employeeDepartmentId", source = "employee.position.department.id"),
            @Mapping(target = "statusId", source = "status.id"),
            @Mapping(target = "statusName", source = "status.name"),
    })
    TaskDto mapTaskToTaskDto(Task task);

    List<TaskDto> mapTasksToTaskDtos(List<Task> tasks);

    @Mappings({
            @Mapping(target = "startDate", source = "startDate", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "dueDate", source = "dueDate", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "actualStartDate", source = "actualStartDate", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "actualEndDate", source = "actualEndDate", dateFormat = CommonConstant.PATTERN_DATE),
    })
    void update(@MappingTarget Task task, TaskUpdateDto taskUpdateDto);

}
