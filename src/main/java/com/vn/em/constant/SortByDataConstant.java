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

    RECOGNITION {
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

    ATTENDANCE {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "year":
                    return "year";
                case "month":
                    return "month";
                case "actualWorkingDays":
                    return "actual_working_days";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },

    SALARY {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "realSalary":
                    return "real_salary";
                case "bonus":
                    return "bonus";
                case "deduction":
                    return "deduction";
                case "netSalary":
                    return "net_salary";
                case "paymentStatus":
                    return "payment_status";
                case "paymentDate":
                    return "payment_date";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },

    PROJECT {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "name":
                    return "name";
                case "budget":
                    return "budget";
                case "startDate":
                    return "start_date";
                case "dueDate":
                    return "due_date";
                case "timelineStart":
                    return "timeline_start";
                case "timelineEnd":
                    return "timeline_end";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },

    TASK {
        @Override
        public String getSortBy(String sortBy) {
            switch (sortBy) {
                case "name":
                    return "name";
                case "budget":
                    return "budget";
                case "startDate":
                    return "start_date";
                case "dueDate":
                    return "due_date";
                case "actualStartDate":
                    return "actual_start_date";
                case "actualEndDate":
                    return "actual_end_date";
                case "lastModifiedDate":
                    return "last_modified_date";
                default:
                    return "created_date";
            }
        }
    },

    MESSAGE {
        @Override
        public String getSortBy(String sortBy) {
            if (sortBy.equals("lastModifiedDate")) {
                return "last_modified_date";
            }
            return "created_date";
        }
    },

    NOTIFICATION {
        @Override
        public String getSortBy(String sortBy) {
            if (sortBy.equals("lastModifiedDate")) {
                return "last_modified_date";
            }
            return "created_date";
        }
    },

}
