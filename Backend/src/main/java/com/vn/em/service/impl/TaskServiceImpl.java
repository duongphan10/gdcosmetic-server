package com.vn.em.service.impl;

import com.vn.em.constant.DataConstant;
import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.constant.SortByDataConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.TaskCreateDto;
import com.vn.em.domain.dto.request.TaskUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.TaskDto;
import com.vn.em.domain.entity.*;
import com.vn.em.domain.mapper.TaskMapper;
import com.vn.em.exception.ForbiddenException;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.*;
import com.vn.em.service.TaskService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto getById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Task.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return taskMapper.mapTaskToTaskDto(task);
    }

    @Override
    public PaginationResponseDto<TaskDto> getAllByProjectId(Integer projectId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{projectId.toString()}));
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.TASK);
        Page<Task> taskPage = taskRepository.getAllByProjectId(projectId, paginationFullRequestDto.getKeyword(), statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.TASK, taskPage);
        List<TaskDto> taskDtos = taskMapper.mapTasksToTaskDtos(taskPage.getContent());

        return new PaginationResponseDto<>(meta, taskDtos);
    }

    @Override
    public PaginationResponseDto<TaskDto> getAllByEmployeeId(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.TASK);
        Page<Task> taskPage = taskRepository.
                getAllByEmployeeId(user.getEmployee().getId(), paginationFullRequestDto.getKeyword(), statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.TASK, taskPage);
        List<TaskDto> taskDtos = taskMapper.mapTasksToTaskDtos(taskPage.getContent());

        return new PaginationResponseDto<>(meta, taskDtos);
    }

    @Override
    public TaskDto create(Integer userId, TaskCreateDto taskCreateDto) {
        Project project = projectRepository.findById(taskCreateDto.getProjectId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{taskCreateDto.getProjectId().toString()}));
        if (!userId.equals(project.getProjectManager().getId())) {
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
        return taskMapper.mapTaskToTaskDto(taskRepository.save(task));
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

        taskMapper.update(task, taskUpdateDto);
        task.setEmployee(employee);
        task.setStatus(status);
        return taskMapper.mapTaskToTaskDto(taskRepository.save(task));
    }

    @Override
    public CommonResponseDto deleteById(Integer userId, Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Task.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        if (!userId.equals(task.getCreatedBy())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        taskRepository.delete(task);
        return new CommonResponseDto(true, MessageConstant.DELETE_TASK_SUCCESSFULLY);
    }

}
