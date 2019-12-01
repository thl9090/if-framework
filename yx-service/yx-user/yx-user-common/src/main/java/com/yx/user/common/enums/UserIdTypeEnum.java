package com.yx.user.common.enums;


/**
 * 用户证件类型 枚举
 * @author TangHuaLiang
 * @since 2018-07-19
 */
public enum UserIdTypeEnum {
    ID_CARD(1,"身份证"),
    ORG_CODE(2,"组织结构代码证");

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
    private UserIdTypeEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(UserIdTypeEnum model:UserIdTypeEnum.values()) {
            if(model.getStatus()==status) {
                return model.getDesc();
            }
        }
        return null;
    }

}
