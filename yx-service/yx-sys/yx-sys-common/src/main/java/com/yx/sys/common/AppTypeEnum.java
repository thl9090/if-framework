package com.yx.sys.common;

/**
 * app类型枚举类
 *
 * @author YanBingHao
 * @since 2018/11/21
 */
public enum AppTypeEnum {

    ANDROID(1, "安卓"),
    IOS(2, "IOS"),
    ;

    private Integer id;
    private String desc;

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    AppTypeEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
