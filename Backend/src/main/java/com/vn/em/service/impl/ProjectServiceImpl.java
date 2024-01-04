package com.vn.em.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.vn.em.constant.*;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.ProjectCreateDto;
import com.vn.em.domain.dto.request.ProjectUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.dto.response.ProjectDto;
import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.entity.Project;
import com.vn.em.domain.entity.Status;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.mapper.ProjectMapper;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.*;
import com.vn.em.service.NotificationService;
import com.vn.em.service.ProjectService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectMapper projectMapper;
    private final NotificationService notificationService;
    private final SocketIOServer server;

    @Override
    public ProjectDto getById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return projectMapper.mapProjectToProjectDto(project);
    }

    @Override
    public List<ProjectDto> getAll(Integer departmentId, Integer statusId) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        List<Project> projects = projectRepository.getAll(departmentId, statusId);
        return projectMapper.mapProjectsToProjectDtos(projects);
    }

    @Override
    public PaginationResponseDto<ProjectDto> search(Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.PROJECT);
        Page<Project> projectPage = projectRepository.search(paginationFullRequestDto.getKeyword(), statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.PROJECT, projectPage);
        List<ProjectDto> projectDtos = projectMapper.mapProjectsToProjectDtos(projectPage.getContent());

        return new PaginationResponseDto<>(meta, projectDtos);
    }

    @Override
    public List<ProjectDto> getAllByUserId(Integer userId, Integer statusId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }
        List<Project> projects = projectRepository.getAllByEmployeeId(user.getEmployee().getId(), statusId);
        return projectMapper.mapProjectsToProjectDtos(projects);
    }

    @Override
    public PaginationResponseDto<ProjectDto> searchByUserId(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.PROJECT);
        Page<Project> projectPage = projectRepository.
                searchByEmployeeId(user.getEmployee().getId(), paginationFullRequestDto.getKeyword(), statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.PROJECT, projectPage);
        List<ProjectDto> projectDtos = projectMapper.mapProjectsToProjectDtos(projectPage.getContent());

        return new PaginationResponseDto<>(meta, projectDtos);
    }

    @Override
    public ProjectDto create(ProjectCreateDto projectCreateDto) {
        Employee employee = null;
        if (projectCreateDto.getProjectManagerId() != null) {
            employee = employeeRepository.findById(projectCreateDto.getProjectManagerId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{projectCreateDto.getProjectManagerId().toString()}));
        }

        Project project = projectMapper.mapProjectCreateDtoToProject(projectCreateDto);
        project.setProjectManager(employee);
        project.setStatus(statusRepository.getById(DataConstant.Status.NEW.getId()));
        projectRepository.save(project);

        if (employee != null && employee.getUser() != null) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.PRO_CREATE.getType(),
                    DataConstant.Notification.PRO_CREATE.getMessage(), employee.getUser().getId());
            server.getRoomOperations(employee.getUser().getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }

        return projectMapper.mapProjectToProjectDto(project);
    }

    @Override
    public ProjectDto updateById(Integer id, ProjectUpdateDto projectUpdateDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        Employee employee = null;
        if (projectUpdateDto.getProjectManagerId() != null) {
            employee = employeeRepository.findById(projectUpdateDto.getProjectManagerId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Employee.ERR_NOT_FOUND_ID, new String[]{projectUpdateDto.getProjectManagerId().toString()}));
        }
        Status status = statusRepository.findById(projectUpdateDto.getStatusId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{projectUpdateDto.getStatusId().toString()}));
        boolean checkStatus = Objects.equals(project.getStatus().getId(), projectUpdateDto.getStatusId());

        projectMapper.update(project, projectUpdateDto);
        project.setProjectManager(employee);
        project.setStatus(status);
        projectRepository.save(project);

        if (employee != null && employee.getUser() != null) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.PRO_CREATE.getType(),
                    DataConstant.Notification.PRO_CREATE.getMessage(), employee.getUser().getId());
            server.getRoomOperations(employee.getUser().getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }
        if (!checkStatus && project.getProjectManager() != null && project.getProjectManager().getUser() != null) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.PRO_UPDATE.getType(),
                    DataConstant.Notification.PRO_UPDATE.getMessage(), project.getProjectManager().getUser().getId());
            server.getRoomOperations(project.getProjectManager().getUser().getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }

        return projectMapper.mapProjectToProjectDto(project);
    }

    @Override
    public CommonResponseDto deleteById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        projectRepository.delete(project);

        if (project.getProjectManager() != null && project.getProjectManager().getUser() != null) {
            NotificationDto notificationDto = notificationService.create(DataConstant.Notification.PRO_DELETE.getType(),
                    DataConstant.Notification.PRO_DELETE.getMessage(), project.getProjectManager().getUser().getId());
            server.getRoomOperations(project.getProjectManager().getUser().getId().toString())
                    .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
        }

        return new CommonResponseDto(true, MessageConstant.DELETE_PROJECT_SUCCESSFULLY);
    }
}
