package com.yx.sys.common;

/**
 * @author YanBingHao
 * @since 2018/12/5
 */
public enum AppVersionStatusEnum {

    UN_PUBLISH(1, "未发布"),
    PUBLISH(2, "已发布"),
    ;

    private Integer id;
    private String desc;

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    AppVersionStatusEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
