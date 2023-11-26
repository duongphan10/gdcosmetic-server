package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.TaskCreateDto;
import com.vn.em.domain.dto.request.TaskUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.TaskDto;

public interface TaskService {

    TaskDto getById(Integer id);

    PaginationResponseDto<TaskDto> getAllByProjectId(Integer projectId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<TaskDto> getAllByEmployeeId(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    TaskDto create(Integer userId, TaskCreateDto taskCreateDto);

    TaskDto updateById(Integer userId, Integer id, TaskUpdateDto taskUpdateDto);

    CommonResponseDto deleteById(Integer userId, Integer id);

}
