package com.yx.user.common.enums;

public enum AccountStatusEnum {
    NO_OPEN(0,"未开通"),
    OPEN(1,"已开通"),
    LOGOUT(2,"拒绝");

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
    private AccountStatusEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(AccountStatusEnum model:AccountStatusEnum.values()) {
            if(model.getStatus()==status) {
                return model.getDesc();
            }
        }
        return null;
    }

}
