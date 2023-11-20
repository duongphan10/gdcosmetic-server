package com.vn.em.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        public static final String LOGIN = PRE_FIX + "/login";
        public static final String LOGOUT = PRE_FIX + "/logout";

        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/user";
        public static final String GET_USER = PRE_FIX + "/{id}";
        public static final String GET_CURRENT_USER = PRE_FIX + "/current";
        public static final String GET_ALL_USER = PRE_FIX + "/all";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String CHANGE_AVATAR = PRE_FIX + "/change/avatar";
        public static final String CHANGE_PASSWORD = PRE_FIX + "/change/password";
        public static final String DELETE = PRE_FIX + "/{id}";

        private User() {
        }
    }

    public static class Department {
        public static final String PRE_FIX = "/department";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Department() {
        }
    }

    public static class Position {
        public static final String PRE_FIX = "/position";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all/{departmentId}";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Position() {
        }
    }

    public static class Employee {
        public static final String PRE_FIX = "/employee";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_BY_CODE = PRE_FIX + "/get";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Employee() {
        }
    }

    public static class SalaryAdjustment {
        public static final String PRE_FIX = "/salary-adjustment";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String GET_ALL_BY_USER_CREATE = PRE_FIX + "/me/all";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private SalaryAdjustment() {
        }
    }

}
