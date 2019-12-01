package com.yx.sys.common;


/**
 * 标的类型枚举定义
 * @author TangHuaLiang
 */
public enum ProductTypeEnum {
    DEBT(1,"债权"),
    LOAN(2,"借款"),
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
    private ProductTypeEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(ProductTypeEnum demo: ProductTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
