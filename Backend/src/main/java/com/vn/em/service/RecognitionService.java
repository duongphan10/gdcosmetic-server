package com.vn.em.service;

import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.request.RecognitionCreateDto;
import com.vn.em.domain.dto.request.RecognitionUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.RecognitionDto;

public interface RecognitionService {

    RecognitionDto getById(Integer id);

    PaginationResponseDto<RecognitionDto> getAll(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    PaginationResponseDto<RecognitionDto> getAllMyCreate(Integer userId, Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto);

    RecognitionDto create(RecognitionCreateDto recognitionCreateDto);

    RecognitionDto updateById(Integer id, RecognitionUpdateDto recognitionUpdateDto);

    CommonResponseDto deleteById(Integer id);
}
