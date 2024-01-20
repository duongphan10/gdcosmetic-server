package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentCreateDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.SalaryAdjustmentDto;

import java.util.List;

public interface SalaryAdjustmentService {

    SalaryAdjustmentDto getById(Integer id);

    List<SalaryAdjustmentDto> getAll(Integer departmentId, Integer statusId);

    PaginationResponseDto<SalaryAdjustmentDto> search(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    List<SalaryAdjustmentDto> getAllMyCreate(Integer userId, Integer statusId);

    PaginationResponseDto<SalaryAdjustmentDto> searchMyCreate(Integer userId, Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    SalaryAdjustmentDto create(SalaryAdjustmentCreateDto salaryAdjustmentCreateDto, Integer userId);

    SalaryAdjustmentDto updateById(Integer id, SalaryAdjustmentUpdateDto salaryAdjustmentUpdateDto, Integer userId);

    CommonResponseDto deleteById(Integer id, Integer userId);
}
