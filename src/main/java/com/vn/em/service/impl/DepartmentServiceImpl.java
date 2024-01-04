package com.vn.em.service.impl;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.domain.dto.request.DepartmentRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.DepartmentDto;
import com.vn.em.domain.entity.Department;
import com.vn.em.domain.mapper.DepartmentMapper;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.DepartmentRepository;
import com.vn.em.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDto getById(Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return departmentMapper.mapDepartmentToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.mapDepartmentsToDepartmentDtos(departments);
    }

    @Override
    public DepartmentDto create(DepartmentRequestDto departmentRequestDto) {
        Department department = departmentMapper.mapDepartmentRequestDtoToDepartment(departmentRequestDto);
        return departmentMapper.mapDepartmentToDepartmentDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto update(Integer id, DepartmentRequestDto departmentRequestDto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        departmentMapper.update(department, departmentRequestDto);
        return departmentMapper.mapDepartmentToDepartmentDto(departmentRepository.save(department));
    }

    @Override
    public CommonResponseDto delete(Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        departmentRepository.delete(department);
        return new CommonResponseDto(true, MessageConstant.DELETE_DEPARTMENT_SUCCESSFULLY);
    }
}
