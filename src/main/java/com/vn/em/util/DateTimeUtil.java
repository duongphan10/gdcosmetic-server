package com.vn.em.util;


import com.vn.em.constant.CommonConstant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String toString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.PATTERN_DATE_TIME);
        return time.format(formatter);
    }

    public static String toString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.PATTERN_DATE);
        return date.format(formatter);
    }

    public static String toStringPathFile(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.PATTERN_DATE_TIME_FILE);
        return time.format(formatter);
    }

    public static boolean isBeforeCurrentMonth(YearMonth yearMonth) {
        YearMonth currentYearMonth = YearMonth.now();
        return yearMonth.isBefore(currentYearMonth);
    }

    public static int getDaysInMonth(YearMonth yearMonth) {
        return yearMonth.lengthOfMonth();
    }

    public static int getWorkingDaysOfMonth(YearMonth yearMonth) {
        int count = 0;
        int daysInMonth = yearMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                count++;
            }
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                count++;
            }
        }
        return daysInMonth - count;
    }

}
