package com.yx.user.common.enums;


/**
 * 用户来源 枚举
 * @author TangHuaLiang
 * @since 2018-07-19
 */
public enum UserSourceEnum {
    PC(1,"PC"),
    APP(2,"APP"),
    WX(3,"微信"),
	ANDROID(4,"安卓"),
	IOS(5,"IOS"),
    ADMIN(6,"ADMIN"),
    API(7,"API")
    ;


    /**
     * 成员变量
     */
    private int status;//状态值
    private String desc;//描述

    public int getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }

    /**
     * 构造方法
     * @param status
     * @param desc
     */
    private UserSourceEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(UserSourceEnum model:UserSourceEnum.values()) {
            if(model.getStatus()==status) {
                return model.getDesc();
            }
        }
        return null;
    }

}
