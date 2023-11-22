package com.vn.em.service.impl;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.constant.SortByDataConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.AttendanceCreateDto;
import com.vn.em.domain.dto.request.AttendanceUpdateDto;
import com.vn.em.domain.dto.response.AttendanceDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.entity.Attendance;
import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.mapper.AttendanceMapper;
import com.vn.em.exception.AlreadyExistException;
import com.vn.em.exception.InvalidException;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.AttendanceRepository;
import com.vn.em.repository.DepartmentRepository;
import com.vn.em.repository.EmployeeRepository;
import com.vn.em.service.AttendanceService;
import com.vn.em.util.DateTimeUtil;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AttendanceDto getById(Integer id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Attendance.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return attendanceMapper.mapAttendanceToAttendanceDto(attendance);
    }

    @Override
    public PaginationResponseDto<AttendanceDto> getAll(Integer year, Integer month, Integer departmentId, PaginationFullRequestDto paginationFullRequestDto) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.ATTENDANCE);

        Page<Attendance> attendancePage = attendanceRepository.getAll(paginationFullRequestDto.getKeyword(), year, month, departmentId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.ATTENDANCE, attendancePage);
        List<AttendanceDto> attendanceDtos = attendanceMapper.mapAttendancesToAttendanceDtos(attendancePage.getContent());

        return new PaginationResponseDto<>(meta, attendanceDtos);
    }

    @Override
    public List<AttendanceDto> create(AttendanceCreateDto attendanceCreateDto) {
        Integer year = attendanceCreateDto.getYear(), month = attendanceCreateDto.getMonth();
        if (!DateTimeUtil.isBeforeCurrentMonth(YearMonth.of(year, month))) {
            throw new InvalidException(ErrorMessage.Attendance.INVALID_YEAR_MONTH);
        }
        if (attendanceRepository.existsByYearAndMonth(year, month)) {
            throw new AlreadyExistException(ErrorMessage.Attendance.ERR_ALREADY_EXIST,
                    new String[]{year.toString(), month.toString()});
        }

        List<Employee> employees = employeeRepository.findAll();
        float workingDaysOfMonth = DateTimeUtil.getWorkingDaysOfMonth(YearMonth.of(year, month));
        float actualWorkingDays = workingDaysOfMonth;

        List<Attendance> attendances = new ArrayList<>();
        for (Employee employee : employees) {
            Attendance attendance = new Attendance();
            attendance.setYear(year);
            attendance.setMonth(month);
            attendance.setActualWorkingDays(actualWorkingDays);
            attendance.setLateArrival(0);
            attendance.setWorkingDaysOfMonth(workingDaysOfMonth);
            attendance.setEmployee(employee);
            attendanceRepository.save(attendance);
            attendances.add(attendance);
        }
        return attendanceMapper.mapAttendancesToAttendanceDtos(attendances);
    }

    @Override
    public AttendanceDto updateById(Integer id, AttendanceUpdateDto attendanceUpdateDto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Attendance.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        attendanceMapper.update(attendance, attendanceUpdateDto);
        return attendanceMapper.mapAttendanceToAttendanceDto(attendanceRepository.save(attendance));
    }

    @Override
    public CommonResponseDto deleteById(Integer id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Attendance.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        attendanceRepository.delete(attendance);
        return new CommonResponseDto(true, MessageConstant.DELETE_ATTENDANCE_SUCCESSFULLY);
    }

}
