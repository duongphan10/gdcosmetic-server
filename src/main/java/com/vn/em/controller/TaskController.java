package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.TaskCreateDto;
import com.vn.em.domain.dto.request.TaskUpdateDto;
import com.vn.em.security.CurrentUser;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.TaskService;
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
public class TaskController {

    private final TaskService taskService;

    @Tag(name = "task-controller")
    @Operation(summary = "API get task by id")
    @GetMapping(UrlConstant.Task.GET_BY_ID)
    public ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        return VsResponseUtil.success(taskService.getById(id));
    }

    @Tag(name = "task-controller")
    @Operation(summary = "API get all task")
    @GetMapping(UrlConstant.Task.GET_ALL)
    public ResponseEntity<?> getAllTask(@Parameter(name = "principal", hidden = true)
                                        @CurrentUser UserPrincipal user,
                                        @RequestParam(required = false) Integer projectId,
                                        @RequestParam(required = false) Integer statusId,
                                        @RequestParam(required = false, defaultValue = "0") Integer type) {
        return VsResponseUtil.success(taskService.getAll(user.getId(), projectId, statusId, type));
    }

    @Tag(name = "task-controller")
    @Operation(summary = "API get my task")
    @GetMapping(UrlConstant.Task.GET_MY_TASK)
    public ResponseEntity<?> getMyTask(@Parameter(name = "principal", hidden = true)
                                       @CurrentUser UserPrincipal user,
                                       @RequestParam(required = false) Integer statusId) {
        return VsResponseUtil.success(taskService.getByEmployeeId(user.getId(), statusId));
    }

    @Tag(name = "task-controller")
    @Operation(summary = "API search my task")
    @GetMapping(UrlConstant.Task.SEARCH_MY_TASK)
    public ResponseEntity<?> searchMyTask(@Parameter(name = "principal", hidden = true)
                                          @CurrentUser UserPrincipal user,
                                          @RequestParam(required = false) Integer statusId,
                                          @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(taskService.searchByEmployeeId(user.getId(), statusId, paginationFullRequestDto));
    }

    @Tag(name = "task-controller")
    @Operation(summary = "API create task")
    @PostMapping(UrlConstant.Task.CREATE)
    @PreAuthorize("hasAnyRole('ROLE_LEADER', 'ROLE_MANAGER')")
    public ResponseEntity<?> createTask(@Parameter(name = "principal", hidden = true)
                                        @CurrentUser UserPrincipal user,
                                        @Valid @RequestBody TaskCreateDto taskCreateDto) {
        return VsResponseUtil.success(taskService.create(user.getId(), taskCreateDto));
    }

    @Tag(name = "task-controller")
    @Operation(summary = "API update task by id")
    @PatchMapping(UrlConstant.Task.UPDATE)
    @PreAuthorize("hasAnyRole('ROLE_LEADER', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateTaskById(@Parameter(name = "principal", hidden = true)
                                            @CurrentUser UserPrincipal user,
                                            @PathVariable Integer id,
                                            @Valid @RequestBody TaskUpdateDto taskUpdateDto) {
        return VsResponseUtil.success(taskService.updateById(user.getId(), id, taskUpdateDto));
    }

    @Tag(name = "task-controller")
    @Operation(summary = "API delete task by id")
    @DeleteMapping(UrlConstant.Task.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_LEADER', 'ROLE_MANAGER')")
    public ResponseEntity<?> deleteTaskById(@Parameter(name = "principal", hidden = true)
                                            @CurrentUser UserPrincipal user,
                                            @PathVariable Integer id) {
        return VsResponseUtil.success(taskService.deleteById(user.getId(), id));
    }

}
