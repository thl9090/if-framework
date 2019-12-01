package com.yx.user.common.enums;

/**
 * 手势密码是否开启
 *
 */
public enum HandPwdIsUsedEnum {
    NO(0,"否"),
    YES(1,"是");

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
    private HandPwdIsUsedEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(HandPwdIsUsedEnum model: HandPwdIsUsedEnum.values()) {
            if(model.getStatus()==status) {
                return model.getDesc();
            }
        }
        return null;
    }

}
