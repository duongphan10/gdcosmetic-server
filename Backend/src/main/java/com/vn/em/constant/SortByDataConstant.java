package com.vn.em.constant;

public enum SortByDataConstant implements SortByInterface {

    USER {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "username":
                    return "username";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },

    EMPLOYEE {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "employeeCode":
                    return "employee_code";
                case "fullName":
                    return "full_name";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },

    SALARY_ADJUSTMENT {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "statusId":
                    return "status_id";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },

}
