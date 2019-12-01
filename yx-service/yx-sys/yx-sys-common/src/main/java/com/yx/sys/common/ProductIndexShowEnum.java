package com.yx.sys.common;


/**
 * 首页标的推送枚举
 * @author TangHuaLiang
 */
public enum ProductIndexShowEnum {
    NO_PUSH(0,"未推送"),
    PUSH(1,"推送"),
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
    private ProductIndexShowEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(ProductIndexShowEnum demo: ProductIndexShowEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
