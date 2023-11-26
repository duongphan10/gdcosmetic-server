package com.vn.em.service.impl;

import com.vn.em.constant.DataConstant;
import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.constant.SortByDataConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.ProjectCreateDto;
import com.vn.em.domain.dto.request.ProjectUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.ProjectDto;
import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.entity.Project;
import com.vn.em.domain.entity.Status;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.mapper.ProjectMapper;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.EmployeeRepository;
import com.vn.em.repository.ProjectRepository;
import com.vn.em.repository.StatusRepository;
import com.vn.em.repository.UserRepository;
import com.vn.em.service.ProjectService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto getById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return projectMapper.mapProjectToProjectDto(project);
    }

    @Override
    public PaginationResponseDto<ProjectDto> getAll(Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.PROJECT);
        Page<Project> projectPage = projectRepository.getAll(paginationFullRequestDto.getKeyword(), statusId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.PROJECT, projectPage);
        List<ProjectDto> projectDtos = projectMapper.mapProjectsToProjectDtos(projectPage.getContent());

        return new PaginationResponseDto<>(meta, projectDtos);
    }

    @Override
    public PaginationResponseDto<ProjectDto> getAllByUserId(Integer userId, Integer statusId, PaginationFullRequestDto paginationFullRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
        if (statusId != null) {
            statusRepository.findById(statusId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Status.ERR_NOT_FOUND_ID, new String[]{statusId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.PROJECT);
        Page<Project> projectPage = projectRepository.
                getAllByEmployeeId(user.getEmployee().getId(), paginationFullRequestDto.getKeyword(), statusId, pageable);
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
        return projectMapper.mapProjectToProjectDto(projectRepository.save(project));
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

        projectMapper.update(project, projectUpdateDto);
        project.setProjectManager(employee);
        project.setStatus(status);
        return projectMapper.mapProjectToProjectDto(projectRepository.save(project));
    }

    @Override
    public CommonResponseDto deleteById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        projectRepository.delete(project);
        return new CommonResponseDto(true, MessageConstant.DELETE_PROJECT_SUCCESSFULLY);
    }
}
