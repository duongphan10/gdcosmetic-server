package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.AttendanceCreateDto;
import com.vn.em.domain.dto.request.AttendanceUpdateDto;
import com.vn.em.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Tag(name = "attendance-controller")
    @Operation(summary = "API get attendance by id")
    @GetMapping(UrlConstant.Attendance.GET_BY_ID)
    public ResponseEntity<?> getAttendanceById(@PathVariable Integer id) {
        return VsResponseUtil.success(attendanceService.getById(id));
    }

    @Tag(name = "attendance-controller")
    @Operation(summary = "API get all attendance")
    @GetMapping(UrlConstant.Attendance.GET_ALL)
    public ResponseEntity<?> getAllAttendance(@RequestParam(name = "year", required = false) Integer year,
                                              @RequestParam(name = "month", required = false) Integer month,
                                              @RequestParam(name = "departmentId", required = false) Integer departmentId,
                                              @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(attendanceService.getAll(year, month, departmentId, paginationFullRequestDto));
    }

    @Tag(name = "attendance-controller")
    @Operation(summary = "API create attendance")
    @PostMapping(UrlConstant.Attendance.CREATE)
    public ResponseEntity<?> createAttendance(@Valid @RequestBody AttendanceCreateDto attendanceCreateDto) {
        return VsResponseUtil.success(attendanceService.create(attendanceCreateDto));
    }

    @Tag(name = "attendance-controller")
    @Operation(summary = "API update attendance by id")
    @PatchMapping(UrlConstant.Attendance.UPDATE)
    public ResponseEntity<?> updateAttendanceById(@PathVariable Integer id,
                                                  @Valid @RequestBody AttendanceUpdateDto attendanceUpdateDto) {
        return VsResponseUtil.success(attendanceService.updateById(id, attendanceUpdateDto));
    }

    @Tag(name = "attendance-controller")
    @Operation(summary = "API delete attendance by id")
    @DeleteMapping(UrlConstant.Attendance.DELETE)
    public ResponseEntity<?> deleteAttendanceById(@PathVariable Integer id) {
        return VsResponseUtil.success(attendanceService.deleteById(id));
    }

}
