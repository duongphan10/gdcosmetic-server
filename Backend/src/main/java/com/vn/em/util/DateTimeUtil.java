package com.vn.em.util;


import com.vn.em.constant.CommonConstant;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
}
