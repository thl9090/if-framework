package com.yx.sys.common;


/**
 * 标的来源枚举定义
 * @author TangHuaLiang
 */
public enum ProductSourceEnum {
    ADMIN_ADD(1,"后台手动发标产生"),
    FLOW_PRODUCT(2,"流标产生"),
    TRANSFER(3,"债转产生")
    ;

    /**
     * 成员变量
     */
    private int status;//状态值
    private String desc;//描述

    public Integer getStatus() {
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
    private ProductSourceEnum(Integer status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(Integer status) {
        for(ProductSourceEnum demo: ProductSourceEnum.values()) {
            if(demo.getStatus().equals(status)) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
