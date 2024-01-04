package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.AttendanceCreateDto;
import com.vn.em.domain.dto.request.AttendanceUpdateDto;
import com.vn.em.domain.dto.response.AttendanceDto;
import com.vn.em.domain.dto.response.CommonResponseDto;

import java.util.List;

public interface AttendanceService {

    AttendanceDto getById(Integer id);

    List<AttendanceDto> getAll(Integer year, Integer month, Integer departmentId);

    PaginationResponseDto<AttendanceDto> search(Integer year, Integer month, Integer departmentId, PaginationFullRequestDto paginationFullRequestDto);

    List<AttendanceDto> create(AttendanceCreateDto attendanceCreateDto);

    AttendanceDto updateById(Integer id, AttendanceUpdateDto attendanceUpdateDto);

    CommonResponseDto deleteById(Integer id);

}
