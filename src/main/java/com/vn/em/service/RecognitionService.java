package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.RecognitionCreateDto;
import com.vn.em.domain.dto.request.RecognitionUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.RecognitionDto;

import java.util.List;

public interface RecognitionService {

    RecognitionDto getById(Integer id);

    List<RecognitionDto> getAll(Integer departmentId, Integer statusId, Boolean type);

    PaginationResponseDto<RecognitionDto> search(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    List<RecognitionDto> getMyCreate(Integer userId, Integer departmentId, Integer statusId, Boolean type);

    PaginationResponseDto<RecognitionDto> searchMyCreate(Integer userId, Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    RecognitionDto create(RecognitionCreateDto recognitionCreateDto, Integer userId);

    RecognitionDto updateById(Integer id, RecognitionUpdateDto recognitionUpdateDto, Integer userId);

    CommonResponseDto deleteById(Integer id, Integer userId);
}
