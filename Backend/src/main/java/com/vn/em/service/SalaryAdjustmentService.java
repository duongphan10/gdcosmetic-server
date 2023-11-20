package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentCreateDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.SalaryAdjustmentDto;

public interface SalaryAdjustmentService {

    SalaryAdjustmentDto getById(Integer id);

    PaginationResponseDto<SalaryAdjustmentDto> getAll(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<SalaryAdjustmentDto> getAllMyCreate(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    SalaryAdjustmentDto create(SalaryAdjustmentCreateDto salaryAdjustmentCreateDto);

    SalaryAdjustmentDto updateById(Integer id, SalaryAdjustmentUpdateDto salaryAdjustmentUpdateDto);

    CommonResponseDto deleteById(Integer id);
}
