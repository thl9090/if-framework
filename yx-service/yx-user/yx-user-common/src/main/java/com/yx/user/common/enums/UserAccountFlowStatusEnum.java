package com.yx.user.common.enums;


/**
 * 用户账户流水记录状态
 * @author TangHuaLiang
 * @since 2018-08-09
 */
public enum UserAccountFlowStatusEnum {
    PENDING(0,"待处理"),
    SUCCESS(1,"成功"),
    FAIL(2,"失败"),
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
    private UserAccountFlowStatusEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(UserAccountFlowStatusEnum model: UserAccountFlowStatusEnum.values()) {
            if(model.getStatus()==status) {
                return model.getDesc();
            }
        }
        return null;
    }

}
