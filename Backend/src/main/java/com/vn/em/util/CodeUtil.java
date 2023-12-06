package com.vn.em.util;

import com.vn.em.constant.CommonConstant;

import java.util.Random;

public class CodeUtil {
    public static String generateEmployeeCode(int departmentId, int code) {
        return CommonConstant.COMPANY_CODE + String.format("%02d", departmentId) + String.format("%05d", code);
    }

    public static String generateCode(int length) {
        String characters = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

}
