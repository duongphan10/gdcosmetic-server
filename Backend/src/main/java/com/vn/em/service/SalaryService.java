package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.SalaryRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.SalaryDto;

import java.util.List;

public interface SalaryService {

    SalaryDto getById(Integer id);

    List<SalaryDto> getAll(Integer year, Integer month, Integer departmentId);

    PaginationResponseDto<SalaryDto> search(Integer year, Integer month, Integer departmentId, PaginationFullRequestDto paginationFullRequestDto);

    List<SalaryDto> create(SalaryRequestDto salaryRequestDto);

    SalaryDto updateById(Integer id);

    List<SalaryDto> payAll(SalaryRequestDto salaryRequestDto);

    SalaryDto payById(Integer id);

    CommonResponseDto deleteById(Integer id);

}
