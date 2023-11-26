package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.ProjectCreateDto;
import com.vn.em.domain.dto.request.ProjectUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.ProjectDto;

public interface ProjectService {

    ProjectDto getById(Integer id);

    PaginationResponseDto<ProjectDto> getAll(Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<ProjectDto> getAllByUserId(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    ProjectDto create(ProjectCreateDto projectCreateDto);

    ProjectDto updateById(Integer id, ProjectUpdateDto projectUpdateDto);

    CommonResponseDto deleteById(Integer id);

}
