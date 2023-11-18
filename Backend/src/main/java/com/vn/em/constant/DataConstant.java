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
        MATERNITY_LEAVE(5, "Maternity Leave");
        private final int id;
        private final String name;
    }
}
