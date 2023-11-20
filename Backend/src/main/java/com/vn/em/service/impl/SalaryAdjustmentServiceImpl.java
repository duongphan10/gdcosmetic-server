package com.vn.em.service.impl;

import com.vn.em.constant.DataConstant;
import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.constant.SortByDataConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.SalaryAdjustmentCreateDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.SalaryAdjustmentDto;
import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.entity.SalaryAdjustment;
import com.vn.em.domain.entity.Status;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.mapper.SalaryAdjustmentMapper;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.*;
import com.vn.em.service.SalaryAdjustmentService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryAdjustmentServiceImpl implements SalaryAdjustmentService {

    private final SalaryAdjustmentRepository salaryAdjustmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final SalaryAdjustmentMapper salaryAdjustmentMapper;

    @Override
    public SalaryAdjustmentDto getById(Integer id) {
        SalaryAdjustment salaryAdjustment = salaryAdjustmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.SalaryAdjustment.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return salaryAdjustmentMapper.mapSalaryAdjustmentToSalaryAdjustmentDto(salaryAdjustment);
    }

    @Override
    public PaginationResponseDto<SalaryAdjustmentDto> getAll(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.SALARY_ADJUSTMENT);

        Page<SalaryAdjustment> salaryAdjustmentPage = salaryAdjustmentRepository.getAll(paginationFullRequestDto.getKeyword(), departmentId, statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.SALARY_ADJUSTMENT, salaryAdjustmentPage);
        List<SalaryAdjustmentDto> salaryAdjustmentDtos = salaryAdjustmentMapper.mapSalaryAdjustmentsToSalaryAdjustmentDtos(salaryAdjustmentPage.getContent());

        return new PaginationResponseDto<>(meta, salaryAdjustmentDtos);
    }

    @Override
    public PaginationResponseDto<SalaryAdjustmentDto> getAllMyCreate(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.SALARY_ADJUSTMENT);

        Page<SalaryAdjustment> salaryAdjustmentPage = salaryAdjustmentRepository.getAllMyCreate(userId, paginationFullRequestDto.getKeyword(), user.getEmployee().getPosition().getDepartment().getId(), statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.SALARY_ADJUSTMENT, salaryAdjustmentPage);
        List<SalaryAdjustmentDto> salaryAdjustmentDtos = salaryAdjustmentMapper.mapSalaryAdjustmentsToSalaryAdjustmentDtos(salaryAdjustmentPage.getContent());

        return new PaginationResponseDto<>(meta, salaryAdjustmentDtos);
    }

    @Override
    public SalaryAdjustmentDto create(SalaryAdjustmentCreateDto salaryAdjustmentCreateDto) {
        Employee employee = employeeRepository.findById(salaryAdjustmentCreateDto.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{salaryAdjustmentCreateDto.getEmployeeId().toString()}));
        SalaryAdjustment salaryAdjustment = salaryAdjustmentMapper.mapSalaryAdjustmentCreateDtoToSalaryAdjustment(salaryAdjustmentCreateDto);
        salaryAdjustment.setEmployee(employee);
        salaryAdjustment.setStatus(statusRepository.getById(DataConstant.Status.PENDING.getId()));
        salaryAdjustmentRepository.save(salaryAdjustment);
        return salaryAdjustmentMapper.mapSalaryAdjustmentToSalaryAdjustmentDto(salaryAdjustment);
    }

    @Override
    public SalaryAdjustmentDto updateById(Integer id, SalaryAdjustmentUpdateDto salaryAdjustmentUpdateDto) {
        SalaryAdjustment salaryAdjustment = salaryAdjustmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.SalaryAdjustment.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        Status status = statusRepository.findById(salaryAdjustmentUpdateDto.getStatusId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{salaryAdjustmentUpdateDto.getStatusId().toString()}));
        salaryAdjustmentMapper.update(salaryAdjustment, salaryAdjustmentUpdateDto);
        salaryAdjustment.setStatus(status);
        salaryAdjustmentRepository.save(salaryAdjustment);
        if (status.getId() == DataConstant.Status.APPROVED.getId()) {
            employeeRepository.updateNewSalary(salaryAdjustment.getEmployee().getId(), salaryAdjustment.getNewSalary());
        }
        return salaryAdjustmentMapper.mapSalaryAdjustmentToSalaryAdjustmentDto(salaryAdjustment);
    }

    @Override
    public CommonResponseDto deleteById(Integer id) {
        SalaryAdjustment salaryAdjustment = salaryAdjustmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.SalaryAdjustment.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        salaryAdjustmentRepository.delete(salaryAdjustment);
        return new CommonResponseDto(true, MessageConstant.DELETE_SALARY_ADJUSTMENT_SUCCESSFULLY);
    }
}
