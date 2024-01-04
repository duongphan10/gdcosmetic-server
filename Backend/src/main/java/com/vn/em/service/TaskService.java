package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.TaskCreateDto;
import com.vn.em.domain.dto.request.TaskUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto getById(Integer id);

    List<TaskDto> getAll(Integer userId, Integer projectId, Integer statusId, Integer type);

    List<TaskDto> getByEmployeeId(Integer userId, Integer statusId);

    PaginationResponseDto<TaskDto> searchByEmployeeId(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    TaskDto create(Integer userId, TaskCreateDto taskCreateDto);

    TaskDto updateById(Integer userId, Integer id, TaskUpdateDto taskUpdateDto);

    CommonResponseDto deleteById(Integer userId, Integer id);

}
