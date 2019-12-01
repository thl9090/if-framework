package com.yx.sys.common;


/**
 * 标的审核状态枚举
 *
 */
public enum ProductCheckStatusEnum {
    PASS(1,"通过"),
    NOT_PASS(2,"不通过");

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
    private ProductCheckStatusEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(ProductCheckStatusEnum demo: ProductCheckStatusEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
