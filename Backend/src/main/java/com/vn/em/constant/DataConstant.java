package com.vn.em.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DataConstant {
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
}
