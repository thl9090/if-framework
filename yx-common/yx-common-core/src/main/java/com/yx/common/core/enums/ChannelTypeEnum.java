package com.yx.common.core.enums;

/**
 * 对接渠道类型
 *
 * @author YanBingHao
 * @since 2018/7/19
 */
public enum ChannelTypeEnum {

    PC(1, "PC"),
    ANDROID(2, "安卓"),
    IOS(3, "IOS"),
    WX(4, "微信"),
    SYSTEM(5, "系统"),
    ;

    private Integer id;
    private String msg;

    public Integer getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    ChannelTypeEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public static ChannelTypeEnum resolve(Integer id) {
        if (id == null) {
            return null;
        }
        for (ChannelTypeEnum typeEnum : ChannelTypeEnum.values()) {
            if (!typeEnum.getId().equals(id)) {
                continue;
            }
            return typeEnum;
        }
        return null;
    }

}
