package com.vn.em.service.impl;

import com.vn.em.constant.CommonConstant;
import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.constant.SortByDataConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.SalaryRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.SalaryDto;
import com.vn.em.domain.entity.Attendance;
import com.vn.em.domain.entity.Salary;
import com.vn.em.domain.mapper.SalaryMapper;
import com.vn.em.exception.AlreadyExistException;
import com.vn.em.exception.ForbiddenException;
import com.vn.em.exception.InvalidException;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.AttendanceRepository;
import com.vn.em.repository.DepartmentRepository;
import com.vn.em.repository.RecognitionRepository;
import com.vn.em.repository.SalaryRepository;
import com.vn.em.service.SalaryService;
import com.vn.em.util.DateTimeUtil;
import com.vn.em.util.PaginationUtil;
import com.vn.em.util.TaxUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final AttendanceRepository attendanceRepository;
    private final DepartmentRepository departmentRepository;
    private final RecognitionRepository recognitionRepository;
    private final SalaryMapper salaryMapper;

    @Override
    public SalaryDto getById(Integer id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Salary.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return salaryMapper.mapSalaryToSalaryDto(salary);
    }

    @Override
    public List<SalaryDto> getAll(Integer year, Integer month, Integer departmentId) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }
        List<Salary> salaries = salaryRepository.getAll(year, month, departmentId);
        return salaryMapper.mapSalariesToSalaryDtos(salaries);
    }

    @Override
    public PaginationResponseDto<SalaryDto> search(Integer year, Integer month, Integer departmentId, PaginationFullRequestDto paginationFullRequestDto) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Department.ERR_NOT_FOUND_ID, new String[]{departmentId.toString()}));
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.SALARY);

        Page<Salary> salaryPage = salaryRepository.search(paginationFullRequestDto.getKeyword(), year, month, departmentId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.SALARY, salaryPage);
        List<SalaryDto> salaryDtos = salaryMapper.mapSalariesToSalaryDtos(salaryPage.getContent());

        return new PaginationResponseDto<>(meta, salaryDtos);
    }

    @Override
    public List<SalaryDto> create(SalaryRequestDto salaryRequestDto) {
        Integer year = salaryRequestDto.getYear(), month = salaryRequestDto.getMonth();
        if (!DateTimeUtil.isBeforeCurrentMonth(YearMonth.of(year, month))) {
            throw new InvalidException(ErrorMessage.Salary.INVALID_YEAR_MONTH);
        }
        if (salaryRepository.countByYearAndMonth(year, month) > 0) {
            throw new AlreadyExistException(ErrorMessage.Salary.ERR_ALREADY_EXIST,
                    new String[]{String.format("%02d", month), year.toString()});
        }
        if (!attendanceRepository.existsByYearAndMonth(year, month)) {
            throw new NotFoundException(ErrorMessage.Attendance.ERR_NOT_FOUND,
                    new String[]{String.format("%02d", month), year.toString()});
        }

        List<Salary> salaries = new ArrayList<>();
        List<Attendance> attendances = attendanceRepository.findAllByYearAndMonth(year, month);
        for (Attendance attendance : attendances) {
            Salary salary = new Salary();
            long employeeSalary = attendance.getEmployee().getSalary();
            long realSalary = (long) ((attendance.getActualWorkingDays() / attendance.getWorkingDaysOfMonth()) * employeeSalary);
            long allowance = CommonConstant.ALLOWANCE;
            long bonus = recognitionRepository.getSumBonus(attendance.getEmployee().getId(), year, month);
            long deduction = recognitionRepository.getSumDeduction(attendance.getEmployee().getId(), year, month);
            long sumSalary = realSalary + allowance + bonus - deduction;
            long insurance = (long) (sumSalary * CommonConstant.INSURANCE);
            long taxableIncome = sumSalary - CommonConstant.PERSONAL_DEDUCTION - insurance;
            taxableIncome = (taxableIncome < 0) ? 0 : taxableIncome;
            long tax = TaxUtil.calculateTax(taxableIncome);
            long netSalary = sumSalary - insurance - tax;

            salary.setRealSalary(realSalary);
            salary.setAllowance(allowance);
            salary.setBonus(bonus);
            salary.setDeduction(deduction);
            salary.setInsurance(insurance);
            salary.setTaxableIncome(taxableIncome);
            salary.setTax(tax);
            salary.setNetSalary(netSalary);
            salary.setPaymentStatus(false);
            salary.setAttendance(attendance);
            salaryRepository.save(salary);
            salaries.add(salary);
        }
        return salaryMapper.mapSalariesToSalaryDtos(salaries);
    }

    @Override
    public SalaryDto updateById(Integer id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Salary.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        if (salary.getPaymentStatus()) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }

        Attendance attendance = salary.getAttendance();
        Integer year = attendance.getYear(), month = attendance.getMonth();

        long employeeSalary = attendance.getEmployee().getSalary();
        long realSalary = (long) ((attendance.getActualWorkingDays() / attendance.getWorkingDaysOfMonth()) * employeeSalary);
        long allowance = CommonConstant.ALLOWANCE;
        long bonus = recognitionRepository.getSumBonus(attendance.getEmployee().getId(), year, month);
        long deduction = recognitionRepository.getSumDeduction(attendance.getEmployee().getId(), year, month);
        long sumSalary = realSalary + allowance + bonus - deduction;
        long insurance = (long) (sumSalary * CommonConstant.INSURANCE);
        long taxableIncome = sumSalary - CommonConstant.PERSONAL_DEDUCTION - insurance;
        taxableIncome = (taxableIncome < 0) ? 0 : taxableIncome;
        long tax = TaxUtil.calculateTax(taxableIncome);
        long netSalary = sumSalary - insurance - tax;

        salary.setRealSalary(realSalary);
        salary.setAllowance(allowance);
        salary.setBonus(bonus);
        salary.setDeduction(deduction);
        salary.setInsurance(insurance);
        salary.setTaxableIncome(taxableIncome);
        salary.setTax(tax);
        salary.setNetSalary(netSalary);

        return salaryMapper.mapSalaryToSalaryDto(salaryRepository.save(salary));
    }

    @Override
    public List<SalaryDto> payAll(SalaryRequestDto salaryRequestDto) {
        Integer year = salaryRequestDto.getYear(), month = salaryRequestDto.getMonth();
        if (salaryRepository.countByYearAndMonth(year, month) == 0) {
            throw new NotFoundException(ErrorMessage.Salary.ERR_NOT_FOUND,
                    new String[]{String.format("%02d", month), year.toString()});
        }
        List<Salary> salaries = salaryRepository.getAllByYearAndMonth(year, month);
        for (Salary salary : salaries) {
            if (!salary.getPaymentStatus()) {
                salary.setPaymentStatus(true);
                salary.setPaymentDate(LocalDateTime.now());
                salaryRepository.save(salary);
            }
        }
        return salaryMapper.mapSalariesToSalaryDtos(salaries);
    }

    @Override
    public SalaryDto payById(Integer id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Salary.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        if (salary.getPaymentStatus()) {
            throw new AlreadyExistException(ErrorMessage.Salary.ERR_SALARY_HAS_BEEN_PAID);
        } else {
            salary.setPaymentStatus(true);
            salary.setPaymentDate(LocalDateTime.now());
        }
        return salaryMapper.mapSalaryToSalaryDto(salaryRepository.save(salary));
    }

    @Override
    public CommonResponseDto deleteById(Integer id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Salary.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        salaryRepository.delete(salary);
        return new CommonResponseDto(true, MessageConstant.DELETE_SALARY_SUCCESSFULLY);
    }
}
