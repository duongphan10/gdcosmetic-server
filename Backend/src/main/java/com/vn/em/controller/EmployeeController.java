package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.EmployeeCreateDto;
import com.vn.em.domain.dto.request.EmployeeUpdateDto;
import com.vn.em.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class EmployeeController {

    private final EmployeeService employeeService;

    @Tag(name = "employee-controller")
    @Operation(summary = "API get employee by id")
    @GetMapping(UrlConstant.Employee.GET_BY_ID)
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
        return VsResponseUtil.success(employeeService.getById(id));
    }

    @Tag(name = "employee-controller")
    @Operation(summary = "API get employee by code")
    @GetMapping(UrlConstant.Employee.GET_BY_CODE)
    public ResponseEntity<?> getEmployeeByCode(@RequestParam String employeeCode) {
        return VsResponseUtil.success(employeeService.getByEmployeeCode(employeeCode));
    }

    @Tag(name = "employee-controller")
    @Operation(summary = "API get all employee")
    @GetMapping(UrlConstant.Employee.GET_ALL)
    public ResponseEntity<?> getAllEmployee(@RequestParam(name = "departmentId", required = false) Integer departmentId,
                                            @RequestParam(name = "statusId", required = false) Integer statusId,
                                            @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(employeeService.getAll(departmentId, statusId, paginationFullRequestDto));
    }

    @Tag(name = "employee-controller")
    @Operation(summary = "API create employee")
    @PostMapping(UrlConstant.Employee.CREATE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> createEmployee(@Valid @ModelAttribute EmployeeCreateDto employeeCreateDto) {
        return VsResponseUtil.success(employeeService.create(employeeCreateDto));
    }

    @Tag(name = "employee-controller")
    @Operation(summary = "API update employee")
    @PatchMapping(UrlConstant.Employee.UPDATE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> updateEmployeeById(@PathVariable Integer id,
                                                @Valid @ModelAttribute EmployeeUpdateDto employeeUpdateDto) {
        return VsResponseUtil.success(employeeService.updateById(id, employeeUpdateDto));
    }

    @Tag(name = "employee-controller")
    @Operation(summary = "API delete employee")
    @DeleteMapping(UrlConstant.Employee.DELETE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Integer id) {
        return VsResponseUtil.success(employeeService.deleteById(id));
    }

}
