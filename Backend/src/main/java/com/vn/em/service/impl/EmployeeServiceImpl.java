package com.vn.em.service.impl;

import com.vn.em.constant.DataConstant;
import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.constant.SortByDataConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.EmployeeCreateDto;
import com.vn.em.domain.dto.request.EmployeeUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.EmployeeDto;
import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.entity.Position;
import com.vn.em.domain.entity.Status;
import com.vn.em.domain.mapper.EmployeeMapper;
import com.vn.em.exception.InvalidException;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.*;
import com.vn.em.service.EmployeeService;
import com.vn.em.util.CodeUtil;
import com.vn.em.util.PaginationUtil;
import com.vn.em.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final StatusRepository statusRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final EmployeeMapper employeeMapper;
    private final UploadFileUtil uploadFileUtil;

    @Override
    public EmployeeDto getById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return employeeMapper.mapEmployeeToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto getByEmployeeCode(String employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_EMPLOYEE_CODE, new String[]{employeeCode}));
        return employeeMapper.mapEmployeeToEmployeeDto(employee);
    }

    @Override
    public PaginationResponseDto<EmployeeDto> getAll(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.EMPLOYEE);

        Page<Employee> employeePage = employeeRepository.getAll(paginationFullRequestDto.getKeyword(), departmentId, statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.EMPLOYEE, employeePage);
        List<EmployeeDto> employeeDtos = employeeMapper.mapEmployeesToEmployeeDtos(employeePage.getContent());

        return new PaginationResponseDto<>(meta, employeeDtos);
    }

    @Override
    public EmployeeDto create(EmployeeCreateDto employeeCreateDto) {
        Position position = positionRepository.findById(employeeCreateDto.getPositionId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Position.ERR_NOT_FOUND_ID, new String[]{employeeCreateDto.getPositionId().toString()}));
        Status status = statusRepository.findById(employeeCreateDto.getStatusId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{employeeCreateDto.getStatusId().toString()}));
        if (employeeCreateDto.getStatusId() != DataConstant.Status.PROBATION.getId() && employeeCreateDto.getStatusId() != DataConstant.Status.ACTIVE.getId()) {
            throw new InvalidException(ErrorMessage.Status.ERR_STATUS_CREATE_EMPLOYEE);
        }

        Employee employee = employeeMapper.mapEmployeeCreateDtoToEmployee(employeeCreateDto);
        employee.setEmployeeCode("");
        employee.setImage(uploadFileUtil.uploadFile(employeeCreateDto.getImage()));
        employee.setPosition(position);
        employee.setStatus(status);
        employeeRepository.save(employee);

        String employeeCode = CodeUtil.generateEmployeeCode(position.getDepartment().getId(), employee.getId());
        employee.setEmployeeCode(employeeCode);

        return employeeMapper.mapEmployeeToEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto updateById(Integer id, EmployeeUpdateDto employeeUpdateDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        Position position = positionRepository.findById(employeeUpdateDto.getPositionId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Position.ERR_NOT_FOUND_ID, new String[]{employeeUpdateDto.getPositionId().toString()}));
        Status status = statusRepository.findById(employeeUpdateDto.getStatusId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{employeeUpdateDto.getStatusId().toString()}));

        employeeMapper.update(employee, employeeUpdateDto);
        if (employeeUpdateDto.getImage() != null) {
            uploadFileUtil.destroyFileWithUrl(employee.getImage());
            employee.setImage(uploadFileUtil.uploadFile(employeeUpdateDto.getImage()));
        }
        if (!employee.getPosition().getDepartment().getId().equals(position.getDepartment().getId())) {
            String newEmployeeCode = CodeUtil.generateEmployeeCode(position.getDepartment().getId(), employee.getId());
            employee.setEmployeeCode(newEmployeeCode);
            employee.setPosition(position);
        }
        employee.setStatus(status);
        if (status.getId() == DataConstant.Status.RESIGNED.getId() || status.getId() == DataConstant.Status.RETIRED.getId()) {
            userRepository.disabledByEmployee(employee.getId());
        }
        return employeeMapper.mapEmployeeToEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    public CommonResponseDto deleteById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        uploadFileUtil.destroyFileWithUrl(employee.getImage());
        employeeRepository.delete(employee);
        return new CommonResponseDto(true, MessageConstant.DELETE_EMPLOYEE_SUCCESSFULLY);
    }
}
