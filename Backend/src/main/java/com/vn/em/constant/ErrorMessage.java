package com.vn.em.constant;

public class ErrorMessage {

    public static final String ERR_EXCEPTION_GENERAL = "exception.general";
    public static final String UNAUTHORIZED = "exception.unauthorized";
    public static final String FORBIDDEN = "exception.forbidden";
    public static final String FORBIDDEN_UPDATE_DELETE = "exception.forbidden.update-delete";
    public static final String ERR_EXCEPTION_DISABLED = "exception.disabled";
    public static final String ERR_EXCEPTION_MAX_UPLOAD_FILE_SIZE = "exception.max.upload.file.size";
    public static final String ERR_EXCEPTION_MULTIPART = "exception.multipart";
    public static final String ERR_EXCEPTION_INVALID_JSON_REQUEST = "exception.invalid.json.request";

    //error validation dto
    public static final String INVALID_SOME_THING_FIELD = "invalid.general";
    public static final String INVALID_FORMAT_SOME_THING_FIELD = "invalid.general.format";
    public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
    public static final String NOT_BLANK_FIELD = "invalid.general.not-blank";
    public static final String INVALID_NUMBER_POSITIVE = "invalid.number.positive";
    public static final String INVALID_NUMBER_POSITIVE_OR_ZERO = "invalid.number.positive.or.zero";
    public static final String INVALID_FORMAT_USERNAME = "invalid.username-format";
    public static final String INVALID_FORMAT_PASSWORD = "invalid.password-format";
    public static final String INVALID_FORMAT_EMAIL = "invalid.email-format";
    public static final String INVALID_DATE = "invalid.date-format";
    public static final String INVALID_DATE_FEATURE = "invalid.date-future";
    public static final String INVALID_DATETIME = "invalid.datetime-format";
    public static final String INVALID_MONTH = "invalid.month";
    public static final String INVALID_YEAR = "invalid.year";
    public static final String INVALID_STATUS_UPDATE_SALARY_ADJUSTMENT = "invalid.status.update.salary.adjustment";
    public static final String INVALID_STATUS_UPDATE_RECOGNITION = "invalid.status.update.recognition";

    public static class Auth {
        public static final String ERR_INCORRECT_USERNAME = "exception.auth.incorrect.username";
        public static final String ERR_INCORRECT_PASSWORD = "exception.auth.incorrect.password";
        public static final String ERR_ACCOUNT_NOT_ENABLED = "exception.auth.account.not.enabled";
        public static final String ERR_ACCOUNT_LOCKED = "exception.auth.account.locked";
        public static final String INVALID_REFRESH_TOKEN = "exception.auth.invalid.refresh.token";
        public static final String EXPIRED_REFRESH_TOKEN = "exception.auth.expired.refresh.token";
    }

    public static class User {
        public static final String ERR_NOT_FOUND_USERNAME = "exception.user.not.found.username";
        public static final String ERR_NOT_FOUND_ID = "exception.user.not.found.id";
        public static final String ERR_ALREADY_EXIST = "exception.user.already.exist";
    }

    public static class Role {
        public static final String ERR_NOT_FOUND_ID = "exception.role.not.found.id";
    }

    public static class Department {
        public static final String ERR_NOT_FOUND_ID = "exception.department.not.found.id";
    }

    public static class Position {
        public static final String ERR_NOT_FOUND_ID = "exception.position.not.found.id";
    }

    public static class Employee {
        public static final String ERR_NOT_FOUND_ID = "exception.employee.not.found.id";
        public static final String ERR_NOT_FOUND_EMPLOYEE_CODE = "exception.employee.not.found.code";
        public static final String ERR_ALREADY_EXIST_USER = "exception.employee.already.exist.account";
    }

    public static class Status {
        public static final String ERR_NOT_FOUND_ID = "exception.status.not.found.id";
        public static final String ERR_STATUS_CREATE_EMPLOYEE = "exception.status.create.employee";
    }

    public static class SalaryAdjustment {
        public static final String ERR_NOT_FOUND_ID = "exception.salary.adjustment.not.found.id";
    }

    public static class Recognition {
        public static final String ERR_NOT_FOUND_ID = "exception.recognition.not.found.id";
    }

    public static class Attendance {
        public static final String ERR_NOT_FOUND_ID = "exception.attendance.not.found.id";
        public static final String ERR_ALREADY_EXIST = "exception.attendance.already.exist";
        public static final String INVALID_YEAR_MONTH = "exception.attendance.invalid.year.month";
        public static final String ERR_NOT_FOUND = "exception.not.found.attendance";
    }

    public static class Salary {
        public static final String ERR_NOT_FOUND_ID = "exception.salary.not.found.id";
        public static final String ERR_ALREADY_EXIST = "exception.salary.already.exist";
        public static final String INVALID_YEAR_MONTH = "exception.salary.invalid.year.month";
        public static final String ERR_NOT_FOUND = "exception.not.found.salary";
        public static final String ERR_SALARY_HAS_BEEN_PAID = "exception.salary.has.been.paid";
    }

}
