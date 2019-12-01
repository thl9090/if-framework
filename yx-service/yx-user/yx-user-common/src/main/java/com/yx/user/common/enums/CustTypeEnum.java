package com.yx.user.common.enums;


/**
 * 客户类型 枚举
 *
 * @author TangHuaLiang
 * @since 2018-07-19
 */
public enum CustTypeEnum {
    PERSONAL_BID(1, "个人出借用户"),
    PERSONAL_BORROWER(2, "个人融资用户"),
    COMPANY_BORROWER(3, "企业融资用户"),
    COMPANY_GUARANTEE(4, "企业担保用户"),

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
     *
     * @param status
     * @param desc
     */
    private CustTypeEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (CustTypeEnum userType : CustTypeEnum.values()) {
            if (userType.getStatus() == status) {
                return userType.getDesc();
            }
        }
        return null;
    }

}
