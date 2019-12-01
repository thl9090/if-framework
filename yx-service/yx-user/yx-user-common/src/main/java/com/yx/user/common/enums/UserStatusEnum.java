package com.yx.user.common.enums;


/**
 * 用户状态 枚举
 * @author TangHuaLiang
 * @since 2018-07-19
 */
public enum UserStatusEnum {
    YES_STATUS(1,"有效"),
    NO_STATUS(0,"无效");

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
    private UserStatusEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(UserStatusEnum model:UserStatusEnum.values()) {
            if(model.getStatus()==status) {
                return model.getDesc();
            }
        }
        return null;
    }

}
