package com.vn.em.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.vn.em.constant.*;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.SalaryAdjustmentCreateDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.dto.response.SalaryAdjustmentDto;
import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.entity.SalaryAdjustment;
import com.vn.em.domain.entity.Status;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.mapper.SalaryAdjustmentMapper;
import com.vn.em.exception.ForbiddenException;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.*;
import com.vn.em.service.NotificationService;
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
    private final RoleRepository roleRepository;
    private final NotificationService notificationService;
    private final SalaryAdjustmentMapper salaryAdjustmentMapper;
    private final SocketIOServer server;

    @Override
    public SalaryAdjustmentDto getById(Integer id) {
        SalaryAdjustment salaryAdjustment = salaryAdjustmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.SalaryAdjustment.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return salaryAdjustmentMapper.mapSalaryAdjustmentToSalaryAdjustmentDto(salaryAdjustment);
    }

    @Override
    public List<SalaryAdjustmentDto> getAll(Integer departmentId, Integer statusId) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        List<SalaryAdjustment> salaryAdjustments = salaryAdjustmentRepository.getAll(departmentId, statusId);
        return salaryAdjustmentMapper.mapSalaryAdjustmentsToSalaryAdjustmentDtos(salaryAdjustments);
    }

    @Override
    public PaginationResponseDto<SalaryAdjustmentDto> search(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.SALARY_ADJUSTMENT);

        Page<SalaryAdjustment> salaryAdjustmentPage = salaryAdjustmentRepository.search(paginationFullRequestDto.getKeyword(), departmentId, statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.SALARY_ADJUSTMENT, salaryAdjustmentPage);
        List<SalaryAdjustmentDto> salaryAdjustmentDtos = salaryAdjustmentMapper.mapSalaryAdjustmentsToSalaryAdjustmentDtos(salaryAdjustmentPage.getContent());

        return new PaginationResponseDto<>(meta, salaryAdjustmentDtos);
    }

    @Override
    public List<SalaryAdjustmentDto> getAllMyCreate(Integer userId, Integer statusId) {
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        List<SalaryAdjustment> salaryAdjustments = salaryAdjustmentRepository.getAllMyCreate(userId, statusId);
        return salaryAdjustmentMapper.mapSalaryAdjustmentsToSalaryAdjustmentDtos(salaryAdjustments);
    }

    @Override
    public PaginationResponseDto<SalaryAdjustmentDto> searchMyCreate(Integer userId, Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.SALARY_ADJUSTMENT);

        Page<SalaryAdjustment> salaryAdjustmentPage = salaryAdjustmentRepository.searchMyCreate(userId, paginationFullRequestDto.getKeyword(), departmentId, statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.SALARY_ADJUSTMENT, salaryAdjustmentPage);
        List<SalaryAdjustmentDto> salaryAdjustmentDtos = salaryAdjustmentMapper.mapSalaryAdjustmentsToSalaryAdjustmentDtos(salaryAdjustmentPage.getContent());

        return new PaginationResponseDto<>(meta, salaryAdjustmentDtos);
    }

    @Override
    public SalaryAdjustmentDto create(SalaryAdjustmentCreateDto salaryAdjustmentCreateDto, Integer userId) {
        Employee employee = employeeRepository.findById(salaryAdjustmentCreateDto.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{salaryAdjustmentCreateDto.getEmployeeId().toString()}));
        SalaryAdjustment salaryAdjustment = salaryAdjustmentMapper.mapSalaryAdjustmentCreateDtoToSalaryAdjustment(salaryAdjustmentCreateDto);
        salaryAdjustment.setOldSalary(employee.getSalary());
        salaryAdjustment.setEmployee(employee);
        salaryAdjustment.setStatus(statusRepository.getById(DataConstant.Status.PENDING.getId()));
        salaryAdjustmentRepository.save(salaryAdjustment);


        List<User> users = userRepository.getAllByRole(roleRepository.findByRoleName(DataConstant.Role.LEADER.getName()));
        for (User user : users) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.SAL_CREATE.getType(),
                    DataConstant.Notification.SAL_CREATE.getMessage(), user, userId);
            server.getRoomOperations(user.getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }

        return salaryAdjustmentMapper.mapSalaryAdjustmentToSalaryAdjustmentDto(salaryAdjustment);
    }

    @Override
    public SalaryAdjustmentDto updateById(Integer id, SalaryAdjustmentUpdateDto salaryAdjustmentUpdateDto, Integer userId) {
        SalaryAdjustment salaryAdjustment = salaryAdjustmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.SalaryAdjustment.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        if (!salaryAdjustment.getStatus().getId().equals(DataConstant.Status.PENDING.getId())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        Status status = statusRepository.findById(salaryAdjustmentUpdateDto.getStatusId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{salaryAdjustmentUpdateDto.getStatusId().toString()}));
        salaryAdjustmentMapper.update(salaryAdjustment, salaryAdjustmentUpdateDto);
        salaryAdjustment.setStatus(status);
        salaryAdjustmentRepository.save(salaryAdjustment);

        NotificationDto notificationDto;
        if (status.getId() == DataConstant.Status.APPROVED.getId()) {
            employeeRepository.updateNewSalary(salaryAdjustment.getEmployee().getId(), salaryAdjustment.getNewSalary());

            if (salaryAdjustment.getEmployee().getUser() != null) {
                notificationDto = notificationService.create(DataConstant.Notification.SAL_UPDATE.getType(),
                        DataConstant.Notification.SAL_UPDATE.getMessage(), salaryAdjustment.getEmployee().getUser(), userId);
                server.getRoomOperations(salaryAdjustment.getEmployee().getUser().getId().toString())
                        .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
            }

            notificationDto = notificationService.create(DataConstant.Notification.SAL_APPROVED.getType(),
                    DataConstant.Notification.SAL_APPROVED.getMessage(), salaryAdjustment.getCreatedBy(), userId);
        } else {
            notificationDto = notificationService.create(DataConstant.Notification.SAL_REJECTED.getType(),
                    DataConstant.Notification.SAL_REJECTED.getMessage(), salaryAdjustment.getCreatedBy(), userId);

        }
        server.getRoomOperations(salaryAdjustment.getCreatedBy().toString())
                .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);

        return salaryAdjustmentMapper.mapSalaryAdjustmentToSalaryAdjustmentDto(salaryAdjustment);
    }

    @Override
    public CommonResponseDto deleteById(Integer id, Integer userId) {
        SalaryAdjustment salaryAdjustment = salaryAdjustmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.SalaryAdjustment.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        if (!salaryAdjustment.getStatus().getId().equals(DataConstant.Status.PENDING.getId())
                || !salaryAdjustment.getCreatedBy().equals(userId)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        salaryAdjustmentRepository.delete(salaryAdjustment);
        return new CommonResponseDto(true, MessageConstant.DELETE_SALARY_ADJUSTMENT_SUCCESSFULLY);
    }
}
