package com.vn.em.service;


import com.vn.em.domain.dto.request.PositionCreateDto;
import com.vn.em.domain.dto.request.PositionUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.PositionDto;

import java.util.List;

public interface PositionService {
    PositionDto getById(Integer id);

    List<PositionDto> getAll(Integer departmentId);

    PositionDto create(PositionCreateDto positionCreateDto);

    PositionDto update(Integer id, PositionUpdateDto positionUpdateDto);

    CommonResponseDto delete(Integer id);
}
