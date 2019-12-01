package com.yx.job.common.enums;

public enum ScheduleStatusEnum {

    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 暂停
     */
    PAUSE(1);

    private Integer id;

    public Integer getId() {
        return id;
    }

    ScheduleStatusEnum(Integer id) {
        this.id = id;
    }
}
