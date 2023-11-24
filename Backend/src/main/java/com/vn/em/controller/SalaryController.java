package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.SalaryRequestDto;
import com.vn.em.service.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class SalaryController {

    private final SalaryService salaryService;

    @Tag(name = "salary-controller")
    @Operation(summary = "API get salary by id")
    @GetMapping(UrlConstant.Salary.GET_BY_ID)
    public ResponseEntity<?> getSalaryById(@PathVariable Integer id) {
        return VsResponseUtil.success(salaryService.getById(id));
    }

    @Tag(name = "salary-controller")
    @Operation(summary = "API get all salary")
    @GetMapping(UrlConstant.Salary.GET_ALL)
    public ResponseEntity<?> getAllSalary(@RequestParam(name = "year", required = false) Integer year,
                                          @RequestParam(name = "month", required = false) Integer month,
                                          @RequestParam(name = "departmentId", required = false) Integer departmentId,
                                          @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(salaryService.getAll(year, month, departmentId, paginationFullRequestDto));
    }

    @Tag(name = "salary-controller")
    @Operation(summary = "API create salary by year and month")
    @PostMapping(UrlConstant.Salary.CREATE)
    public ResponseEntity<?> createSalary(@Valid @RequestBody SalaryRequestDto salaryRequestDto) {
        return VsResponseUtil.success(salaryService.create(salaryRequestDto));
    }

    @Tag(name = "salary-controller")
    @Operation(summary = "API update salary by id")
    @PatchMapping(UrlConstant.Salary.UPDATE)
    public ResponseEntity<?> updateSalaryById(@PathVariable Integer id) {
        return VsResponseUtil.success(salaryService.updateById(id));
    }

    @Tag(name = "salary-controller")
    @Operation(summary = "API pay all salary by year and month")
    @PatchMapping(UrlConstant.Salary.PAY_ALL)
    public ResponseEntity<?> payAllSalaryByYearAndMonth(@Valid @RequestBody SalaryRequestDto salaryRequestDto) {
        return VsResponseUtil.success(salaryService.payAll(salaryRequestDto));
    }

    @Tag(name = "salary-controller")
    @Operation(summary = "API pay salary by id")
    @PatchMapping(UrlConstant.Salary.PAY_BY_ID)
    public ResponseEntity<?> paySalaryById(@PathVariable Integer id) {
        return VsResponseUtil.success(salaryService.payById(id));
    }

    @Tag(name = "salary-controller")
    @Operation(summary = "API delete salary by id")
    @DeleteMapping(UrlConstant.Salary.DELETE)
    public ResponseEntity<?> deleteSalaryById(@PathVariable Integer id) {
        return VsResponseUtil.success(salaryService.deleteById(id));
    }

}
