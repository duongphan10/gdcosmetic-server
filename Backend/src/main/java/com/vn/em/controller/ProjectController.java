package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.ProjectCreateDto;
import com.vn.em.domain.dto.request.ProjectUpdateDto;
import com.vn.em.security.CurrentUser;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class ProjectController {

    private final ProjectService projectService;

    @Tag(name = "project-controller")
    @Operation(summary = "API get project by id")
    @GetMapping(UrlConstant.Project.GET_BY_ID)
    public ResponseEntity<?> getProjectById(@PathVariable Integer id) {
        return VsResponseUtil.success(projectService.getById(id));
    }

    @Tag(name = "project-controller")
    @Operation(summary = "API get all project")
    @GetMapping(UrlConstant.Project.GET_ALL)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> getAllProject(@RequestParam(required = false) Integer statusId,
                                           @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(projectService.getAll(statusId, paginationFullRequestDto));
    }

    @Tag(name = "project-controller")
    @Operation(summary = "API get all my project")
    @GetMapping(UrlConstant.Project.GET_ALL_MY_PROJECT)
    @PreAuthorize("hasAnyRole('ROLE_LEADER', 'ROLE_MANAGER')")
    public ResponseEntity<?> getAllMyProject(@Parameter(name = "principal", hidden = true)
                                             @CurrentUser UserPrincipal user,
                                             @RequestParam(required = false) Integer statusId,
                                             @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(projectService.getAllByUserId(user.getId(), statusId, paginationFullRequestDto));
    }

    @Tag(name = "project-controller")
    @Operation(summary = "API create project")
    @PostMapping(UrlConstant.Project.CREATE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectCreateDto projectCreateDto) {
        return VsResponseUtil.success(projectService.create(projectCreateDto));
    }

    @Tag(name = "project-controller")
    @Operation(summary = "API update project by id")
    @PatchMapping(UrlConstant.Project.UPDATE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> updateProjectById(@PathVariable Integer id,
                                               @Valid @RequestBody ProjectUpdateDto projectUpdateDto) {
        return VsResponseUtil.success(projectService.updateById(id, projectUpdateDto));
    }

    @Tag(name = "project-controller")
    @Operation(summary = "API delete project by id")
    @DeleteMapping(UrlConstant.Project.DELETE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> deleteProjectById(@PathVariable Integer id) {
        return VsResponseUtil.success(projectService.deleteById(id));
    }

}
