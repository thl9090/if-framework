package com.yx.business.common.enums;


/**
 * 债权转让类型枚举定义
 *
 */
public enum TransferTypeEnum {
    PRODUCT(1,"散标债权"),
    SMART_BID_TRANSFER1(2,"智投债转1（需要计算利息）"),
    SMART_BID_TRANSFER2(3,"智投债转2（不需要计算利息）"),
    ;

    /**
     * 成员变量
     */
    private Integer status;//状态值
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
    private TransferTypeEnum(Integer status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(Integer status) {
        for(TransferTypeEnum demo: TransferTypeEnum.values()) {
            if(demo.getStatus().equals(status)) {
                return demo.getDesc();
            }
        }
        return null;
    }

    public static TransferTypeEnum resolve(Integer status) {
        for(TransferTypeEnum demo: TransferTypeEnum.values()) {
            if(demo.getStatus().equals(status)) {
                return demo;
            }
        }
        return null;
    }

}
