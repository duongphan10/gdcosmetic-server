package com.vn.em.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.vn.em.constant.*;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.RecognitionCreateDto;
import com.vn.em.domain.dto.request.RecognitionUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.dto.response.RecognitionDto;
import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.entity.Recognition;
import com.vn.em.domain.entity.Status;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.mapper.RecognitionMapper;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.*;
import com.vn.em.service.NotificationService;
import com.vn.em.service.RecognitionService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecognitionServiceImpl implements RecognitionService {
    private final RecognitionRepository recognitionRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final NotificationService notificationService;
    private final RecognitionMapper recognitionMapper;
    private final SocketIOServer server;

    @Override
    public RecognitionDto getById(Integer id) {
        Recognition recognition = recognitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Recognition.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return recognitionMapper.mapRecognitionToRecognitionDto(recognition);
    }

    @Override
    public PaginationResponseDto<RecognitionDto> getAll(Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.RECOGNITION);

        Page<Recognition> recognitionPage = recognitionRepository.getAll(paginationFullRequestDto.getKeyword(), departmentId, statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.RECOGNITION, recognitionPage);
        List<RecognitionDto> recognitionDtos = recognitionMapper.mapRecognitionsToRecognitionDtos(recognitionPage.getContent());

        return new PaginationResponseDto<>(meta, recognitionDtos);
    }

    @Override
    public PaginationResponseDto<RecognitionDto> getAllMyCreate(Integer userId, Integer departmentId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.RECOGNITION);

        Page<Recognition> recognitionPage = recognitionRepository.getAllMyCreate(userId, paginationFullRequestDto.getKeyword(), departmentId, statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.RECOGNITION, recognitionPage);
        List<RecognitionDto> recognitionDtos = recognitionMapper.mapRecognitionsToRecognitionDtos(recognitionPage.getContent());

        return new PaginationResponseDto<>(meta, recognitionDtos);
    }

    @Override
    public RecognitionDto create(RecognitionCreateDto recognitionCreateDto) {
        Employee employee = employeeRepository.findById(recognitionCreateDto.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{recognitionCreateDto.getEmployeeId().toString()}));
        Recognition recognition = recognitionMapper.mapRecognitionCreateDtoToRecognition(recognitionCreateDto);
        recognition.setEmployee(employee);
        recognition.setStatus(statusRepository.getById(DataConstant.Status.PENDING.getId()));
        recognitionRepository.save(recognition);

        List<User> users = userRepository.getAllByRole(roleRepository.findByRoleName(DataConstant.Role.LEADER.getName()));
        for (User user : users) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.SAL_CREATE.getType(),
                    DataConstant.Notification.REC_CREATE.getMessage(), user);
            server.getRoomOperations(user.getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }

        return recognitionMapper.mapRecognitionToRecognitionDto(recognition);
    }

    @Override
    public RecognitionDto updateById(Integer id, RecognitionUpdateDto recognitionUpdateDto) {
        Recognition recognition = recognitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Recognition.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        Status status = statusRepository.findById(recognitionUpdateDto.getStatusId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{recognitionUpdateDto.getStatusId().toString()}));
        recognitionMapper.update(recognition, recognitionUpdateDto);
        recognition.setDate(LocalDate.now());
        recognition.setStatus(status);
        recognitionRepository.save(recognition);

        NotificationDto notificationDto;
        if (status.getId() == DataConstant.Status.APPROVED.getId()) {
            notificationDto = notificationService.create(DataConstant.Notification.REC_APPROVED.getType(),
                    DataConstant.Notification.REC_APPROVED.getMessage(), recognition.getCreatedBy());
        } else {
            notificationDto = notificationService.create(DataConstant.Notification.REC_REJECTED.getType(),
                    DataConstant.Notification.REC_REJECTED.getMessage(), recognition.getCreatedBy());
        }
        server.getRoomOperations(recognition.getCreatedBy().toString())
                .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);

        return recognitionMapper.mapRecognitionToRecognitionDto(recognition);
    }

    @Override
    public CommonResponseDto deleteById(Integer id) {
        Recognition recognition = recognitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Recognition.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        recognitionRepository.delete(recognition);
        return new CommonResponseDto(true, MessageConstant.DELETE_RECOGNITION_SUCCESSFULLY);
    }

}
