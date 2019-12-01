package com.yx.user.common.enums;


/**
 * 用户账户流水记录类型
 * @author TangHuaLiang
 * @since 2018-08-09
 */
public enum UserAccountFlowTypeEnum {
    OPEN_ACCOUT_BID(1,"个人开户-投资用户"),
    OPEN_ACCOUT_BORROW(2,"个人开户-融资用户"),
    COMPANY_OPEN_ACCOUNT(3,"企业开户"),
    BAND_CARD(5,"解绑"),
    ACCOUNT_UPDATE(6,"更新账户绑定信息"),
    REGISTERPHONE_UPDATE(7,"注册手机号修改"),

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
    private UserAccountFlowTypeEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(UserAccountFlowTypeEnum model: UserAccountFlowTypeEnum.values()) {
            if(model.getStatus()==status) {
                return model.getDesc();
            }
        }
        return null;
    }

}
