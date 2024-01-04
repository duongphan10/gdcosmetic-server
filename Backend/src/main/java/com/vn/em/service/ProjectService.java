package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.ProjectCreateDto;
import com.vn.em.domain.dto.request.ProjectUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.ProjectDto;

import java.util.List;

public interface ProjectService {

    ProjectDto getById(Integer id);

    List<ProjectDto> getAll(Integer departmentId, Integer statusId);

    PaginationResponseDto<ProjectDto> search(Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    List<ProjectDto> getAllByUserId(Integer userId, Integer statusId);

    PaginationResponseDto<ProjectDto> searchByUserId(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    ProjectDto create(ProjectCreateDto projectCreateDto);

    ProjectDto updateById(Integer id, ProjectUpdateDto projectUpdateDto);

    CommonResponseDto deleteById(Integer id);

}
