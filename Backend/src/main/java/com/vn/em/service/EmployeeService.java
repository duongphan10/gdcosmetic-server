package com.vn.em.service;


import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.EmployeeCreateDto;
import com.vn.em.domain.dto.request.EmployeeUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto getById(Integer id);

    EmployeeDto getByEmployeeCode(String employeeCode);

    List<EmployeeDto> getAll(Integer departmentId, Integer statusId);

    PaginationResponseDto<EmployeeDto> search(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    EmployeeDto create(EmployeeCreateDto employeeCreateDto);

    EmployeeDto updateById(Integer id, EmployeeUpdateDto employeeUpdateDto);

    CommonResponseDto deleteById(Integer id);

}
