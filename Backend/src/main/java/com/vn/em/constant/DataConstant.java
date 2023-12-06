package com.vn.em.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DataConstant {

    @Getter
    @AllArgsConstructor
    public enum Role {
        LEADER(1, "ROLE_LEADER"),
        MANAGER(2, "ROLE_MANAGER"),
        EMPLOYEE(3, "ROLE_EMPLOYEE");
        private final Integer id;
        private final String name;
    }


    @Getter
    @AllArgsConstructor
    public enum Status {
        ACTIVE(1, "Active"),
        RESIGNED(2, "Resigned"),
        PROBATION(3, "Probation"),
        RETIRED(4, "Retired"),
        MATERNITY_LEAVE(5, "Maternity Leave"),
        PENDING(6, "Pending"),
        APPROVED(7, "Approved"),
        REJECTED(8, "Rejected"),
        NEW(9, "New"),
        PROCESSING(10, "Processing"),
        PAUSED(11, "Paused "),
        COMPLETED(12, "Completed"),
        CANCELED(13, "Canceled");
        private final int id;
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum Notification {
        SAL_CREATE(1, "There has been a new request for a salary adjustment"),
        SAL_APPROVED(2, "The request to adjust the salary has been accepted"),
        SAL_REJECTED(3, "The request to adjust the salary has been rejected"),
        SAL_UPDATE(4, "Your salary has been changed"),
        REC_CREATE(5, "There has been a new request for reward or discipline"),
        REC_APPROVED(6, "Your request for reward or discipline has been accepted"),
        REC_REJECTED(7, "Your request for reward or discipline has been rejected"),
        PRO_CREATE(8, "You have been assigned to a new project"),
        PRO_UPDATE(9, "The project you manage has had its status changed"),
        PRO_DELETE(10, "The project you managed has been deleted"),
        TAS_CREATE(11, "You have been assigned to a new task"),
        TAS_UPDATE(12, "The project manager has changed the status of your task"),
        TAS_DELETE(13, "The project manager has deleted your task");
        private final int type;
        private final String message;
    }

}
