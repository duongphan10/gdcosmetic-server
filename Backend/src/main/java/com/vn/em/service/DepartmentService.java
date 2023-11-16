package com.vn.em.service;


import com.vn.em.domain.dto.request.DepartmentRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto getById(Integer id);

    List<DepartmentDto> getAll();

    DepartmentDto create(DepartmentRequestDto departmentRequestDto);

    DepartmentDto update(Integer id, DepartmentRequestDto departmentRequestDto);

    CommonResponseDto delete(Integer id);
}
