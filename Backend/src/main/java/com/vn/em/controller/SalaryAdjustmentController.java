package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentCreateDto;
import com.vn.em.domain.dto.request.SalaryAdjustmentUpdateDto;
import com.vn.em.security.CurrentUser;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.SalaryAdjustmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class SalaryAdjustmentController {

    private final SalaryAdjustmentService salaryAdjustmentService;

    @Tag(name = "salary-adjustment-controller")
    @Operation(summary = "API get salary adjustment by id")
    @GetMapping(UrlConstant.SalaryAdjustment.GET_BY_ID)
    public ResponseEntity<?> getSalaryAdjustmentById(@PathVariable Integer id) {
        return VsResponseUtil.success(salaryAdjustmentService.getById(id));
    }

    @Tag(name = "salary-adjustment-controller")
    @Operation(summary = "API get all salary adjustment")
    @GetMapping(UrlConstant.SalaryAdjustment.GET_ALL)
    public ResponseEntity<?> getAllSalaryAdjustment(@RequestParam(name = "departmentId", required = false) Integer departmentId,
                                                    @RequestParam(name = "statusId", required = false) Integer statusId,
                                                    @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(salaryAdjustmentService.getAll(departmentId, statusId, paginationFullRequestDto));
    }

    @Tag(name = "salary-adjustment-controller")
    @Operation(summary = "API get all my salary adjustment create")
    @GetMapping(UrlConstant.SalaryAdjustment.GET_ALL_BY_USER_CREATE)
    public ResponseEntity<?> getAllMySalaryAdjustmentCreate(@RequestParam(name = "statusId", required = false) Integer statusId,
                                                            @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto,
                                                            @Parameter(name = "principal", hidden = true)
                                                            @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(salaryAdjustmentService.getAllMyCreate(user.getId(), statusId, paginationFullRequestDto));
    }

    @Tag(name = "salary-adjustment-controller")
    @Operation(summary = "API create salary adjustment")
    @PostMapping(UrlConstant.SalaryAdjustment.CREATE)
    public ResponseEntity<?> createSalaryAdjustment(@Valid @RequestBody SalaryAdjustmentCreateDto salaryAdjustmentCreateDto) {
        return VsResponseUtil.success(salaryAdjustmentService.create(salaryAdjustmentCreateDto));
    }

    @Tag(name = "salary-adjustment-controller")
    @Operation(summary = "API update salary adjustment by id")
    @PatchMapping(UrlConstant.SalaryAdjustment.UPDATE)
    public ResponseEntity<?> updateSalaryAdjustmentById(@PathVariable Integer id,
                                                        @Valid @RequestBody SalaryAdjustmentUpdateDto salaryAdjustmentUpdateDto) {
        return VsResponseUtil.success(salaryAdjustmentService.updateById(id, salaryAdjustmentUpdateDto));
    }

    @Tag(name = "salary-adjustment-controller")
    @Operation(summary = "API delete salary adjustment by id")
    @DeleteMapping(UrlConstant.SalaryAdjustment.DELETE)
    public ResponseEntity<?> deleteSalaryAdjustmentById(@PathVariable Integer id) {
        return VsResponseUtil.success(salaryAdjustmentService.deleteById(id));
    }


}
