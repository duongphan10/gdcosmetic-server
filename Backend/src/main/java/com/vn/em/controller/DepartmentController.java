package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.request.DepartmentRequestDto;
import com.vn.em.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class DepartmentController {

    private final DepartmentService departmentService;

    @Tag(name = "department-controller")
    @Operation(summary = "API get department by id")
    @GetMapping(UrlConstant.Department.GET_BY_ID)
    public ResponseEntity<?> getDepartmentById(@PathVariable Integer id) {
        return VsResponseUtil.success(departmentService.getById(id));
    }

    @Tag(name = "department-controller")
    @Operation(summary = "API get all department")
    @GetMapping(UrlConstant.Department.GET_ALL)
    public ResponseEntity<?> getAllDepartment() {
        return VsResponseUtil.success(departmentService.getAll());
    }

    @Tag(name = "department-controller")
    @Operation(summary = "API create department")
    @PostMapping(UrlConstant.Department.CREATE)
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        return VsResponseUtil.success(departmentService.create(departmentRequestDto));
    }

    @Tag(name = "department-controller")
    @Operation(summary = "API update department")
    @PatchMapping(UrlConstant.Department.UPDATE)
    public ResponseEntity<?> updateDepartment(@PathVariable Integer id,
                                              @Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        return VsResponseUtil.success(departmentService.update(id, departmentRequestDto));
    }

    @Tag(name = "department-controller")
    @Operation(summary = "API delete department")
    @DeleteMapping(UrlConstant.Department.DELETE)
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id) {
        return VsResponseUtil.success(departmentService.delete(id));
    }

}
