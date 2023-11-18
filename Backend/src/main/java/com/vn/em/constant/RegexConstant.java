package com.vn.em.constant;

public class RegexConstant {
    public static final String COMMON_REGEX = "^[\\p{L}\\p{N} .,-]{1,255}$";
    public static final String STRING_REGEX = "^.{1,255}$";

    public static final String USERNAME = "^[a-z][a-z0-9]{3,15}$";
    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";

}
