package com.yx.sys.common;


/**
 * 标的审核数据类型
 *
 */
public enum ProductCheckTypeEnum {
    PRODUCT(1,"散标"),
    SMART_BID(2,"智投");

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
    private ProductCheckTypeEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(ProductCheckTypeEnum demo: ProductCheckTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
