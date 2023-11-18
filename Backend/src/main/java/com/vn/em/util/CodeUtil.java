package com.vn.em.util;

import com.vn.em.constant.CommonConstant;

public class CodeUtil {
    public static String generateEmployeeCode(int departmentId, int code) {
        return CommonConstant.COMPANY_CODE + String.format("%02d", departmentId) + String.format("%05d", code);
    }

}
