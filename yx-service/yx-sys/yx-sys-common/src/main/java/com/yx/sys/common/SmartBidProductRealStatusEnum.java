package com.yx.sys.common;


/**
 *  智投
 * 所有的枚举定义都以enum结束
 */
public enum SmartBidProductRealStatusEnum {
    YES(1,"有效"),
    REMOVE(0,"移除");

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
    private SmartBidProductRealStatusEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(SmartBidProductRealStatusEnum demo: SmartBidProductRealStatusEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
