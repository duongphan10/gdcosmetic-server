package com.vn.em.service.impl;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.domain.dto.request.PositionCreateDto;
import com.vn.em.domain.dto.request.PositionUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.PositionDto;
import com.vn.em.domain.entity.Department;
import com.vn.em.domain.entity.Position;
import com.vn.em.domain.mapper.PositionMapper;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.DepartmentRepository;
import com.vn.em.repository.PositionRepository;
import com.vn.em.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionMapper positionMapper;

    @Override
    public PositionDto getById(Integer id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Position.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return positionMapper.mapPositionToPositionDto(position);
    }

    @Override
    public List<PositionDto> getAll(Integer departmentId) {
        if (departmentId > 0) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        List<Position> positions = positionRepository.getAllByDepartmentId(departmentId);
        return positionMapper.mapPositionsToPositionDtos(positions);
    }

    @Override
    public PositionDto create(PositionCreateDto positionCreateDto) {
        Department department = departmentRepository.findById(positionCreateDto.getDepartmentId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{positionCreateDto.getDepartmentId().toString()}));
        Position position = positionMapper.mapPositionCreateDtoToPosition(positionCreateDto);
        position.setDepartment(department);
        return positionMapper.mapPositionToPositionDto(positionRepository.save(position));
    }

    @Override
    public PositionDto update(Integer id, PositionUpdateDto positionUpdateDto) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Position.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        positionMapper.update(position, positionUpdateDto);
        return positionMapper.mapPositionToPositionDto(positionRepository.save(position));
    }

    @Override
    public CommonResponseDto delete(Integer id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Position.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        positionRepository.delete(position);
        return new CommonResponseDto(true, MessageConstant.DELETE_POSITION_SUCCESSFULLY);
    }
}
