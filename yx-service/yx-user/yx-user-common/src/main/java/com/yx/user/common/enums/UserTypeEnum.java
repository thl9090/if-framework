package com.yx.user.common.enums;


/**
 * 用户类型 枚举
 * @author TangHuaLiang
 * @since 2018-07-19
 */
public enum UserTypeEnum {
    PERSONAL(1,"个人用户"),
    COMPANY(2,"企业用户")
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
    private UserTypeEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(UserTypeEnum userType:UserTypeEnum.values()) {
            if(userType.getStatus()==status) {
                return userType.getDesc();
            }
        }
        return null;
    }

}
