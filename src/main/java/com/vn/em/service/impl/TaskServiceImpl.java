package com.vn.em.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.vn.em.constant.*;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.TaskCreateDto;
import com.vn.em.domain.dto.request.TaskUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.dto.response.TaskDto;
import com.vn.em.domain.entity.*;
import com.vn.em.domain.mapper.TaskMapper;
import com.vn.em.exception.ForbiddenException;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.*;
import com.vn.em.service.NotificationService;
import com.vn.em.service.TaskService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final TaskMapper taskMapper;
    private final NotificationService notificationService;
    private final SocketIOServer server;

    @Override
    public TaskDto getById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Task.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return taskMapper.mapTaskToTaskDto(task);
    }

    @Override
    public List<TaskDto> getAll(Integer userId, Integer projectId, Integer statusId, Integer type) {
        if (projectId != null) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{projectId.toString()}));
        }
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        List<Task> tasks = new ArrayList<>();
        if (type == 0) {
            tasks = taskRepository.getAll(projectId, statusId);
        } else {
            if (type == 1) {
                tasks = taskRepository.getAllMyCreated(userId, projectId, statusId);
            }
        }
        return taskMapper.mapTasksToTaskDtos(tasks);
    }

    @Override
    public List<TaskDto> getByEmployeeId(Integer userId, Integer statusId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        List<Task> tasks = taskRepository.getByEmployeeId(user.getEmployee().getId(), statusId);
        return taskMapper.mapTasksToTaskDtos(tasks);
    }

    @Override
    public PaginationResponseDto<TaskDto> searchByEmployeeId(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.TASK);
        Page<Task> taskPage = taskRepository.
                searchByEmployeeId(user.getEmployee().getId(), paginationFullRequestDto.getKeyword(), statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.TASK, taskPage);
        List<TaskDto> taskDtos = taskMapper.mapTasksToTaskDtos(taskPage.getContent());

        return new PaginationResponseDto<>(meta, taskDtos);
    }

    @Override
    public TaskDto create(Integer userId, TaskCreateDto taskCreateDto) {
        Project project = projectRepository.findById(taskCreateDto.getProjectId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{taskCreateDto.getProjectId().toString()}));
        if (!userId.equals(project.getProjectManager().getUser().getId())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }
        Employee employee = null;
        if (taskCreateDto.getEmployeeId() != null) {
            employee = employeeRepository.findById(taskCreateDto.getEmployeeId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{taskCreateDto.getEmployeeId().toString()}));
        }

        Task task = taskMapper.mapTaskCreateDtoToTask(taskCreateDto);
        task.setProject(project);
        task.setEmployee(employee);
        task.setStatus(statusRepository.getById(DataConstant.Status.NEW.getId()));
        taskRepository.save(task);

        if (employee != null && employee.getUser() != null) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.TAS_CREATE.getType(),
                    DataConstant.Notification.TAS_CREATE.getMessage(), employee.getUser().getId(), userId);
            server.getRoomOperations(employee.getUser().getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }

        return taskMapper.mapTaskToTaskDto(task);
    }

    @Override
    public TaskDto updateById(Integer userId, Integer id, TaskUpdateDto taskUpdateDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Task.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        if (!userId.equals(task.getCreatedBy())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        Employee employee = null;
        if (taskUpdateDto.getEmployeeId() != null) {
            employee = employeeRepository.findById(taskUpdateDto.getEmployeeId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{taskUpdateDto.getEmployeeId().toString()}));
        }
        Status status = statusRepository.findById(taskUpdateDto.getStatusId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{taskUpdateDto.getStatusId().toString()}));
        boolean checkStatus = Objects.equals(task.getStatus().getId(), taskUpdateDto.getStatusId());
        taskMapper.update(task, taskUpdateDto);
        task.setEmployee(employee);
        task.setStatus(status);
        taskRepository.save(task);

        if (employee != null && employee.getUser() != null) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.TAS_CREATE.getType(),
                    DataConstant.Notification.TAS_CREATE.getMessage(), employee.getUser().getId(), userId);
            server.getRoomOperations(employee.getUser().getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }
        if (!checkStatus && task.getEmployee() != null && task.getEmployee().getUser() != null) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.TAS_UPDATE.getType(),
                    DataConstant.Notification.TAS_UPDATE.getMessage(), task.getEmployee().getUser().getId(), userId);
            server.getRoomOperations(task.getEmployee().getUser().getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }

        return taskMapper.mapTaskToTaskDto(task);
    }

    @Override
    public CommonResponseDto deleteById(Integer userId, Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Task.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        if (!userId.equals(task.getCreatedBy())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        taskRepository.delete(task);

        if (task.getEmployee() != null && task.getEmployee().getUser() != null) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.TAS_DELETE.getType(),
                    DataConstant.Notification.TAS_DELETE.getMessage(), task.getEmployee().getUser().getId(), userId);
            server.getRoomOperations(task.getEmployee().getUser().getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }

        return new CommonResponseDto(true, MessageConstant.DELETE_TASK_SUCCESSFULLY);
    }

}
