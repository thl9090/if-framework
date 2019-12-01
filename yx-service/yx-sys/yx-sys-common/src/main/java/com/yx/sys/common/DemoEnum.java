package com.yx.sys.common;


/**
 * 枚举定义demo
 * 所有的枚举定义都以enum结束
 */
public enum DemoEnum {
    YES(1,"是"),
    NO(2,"否");

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
    private DemoEnum(int status,String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(DemoEnum demo:DemoEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
