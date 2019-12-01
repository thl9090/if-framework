package com.yx.sys.common;


/**
 * 枚举定义
 * lilulu
 * 20180816
 */
public enum SlideShowStatusEnum {

    UNPUBLISH(0,"待发布"),
    PUBLISH(1,"发布"),
    DOWNLINE(2,"下线");

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
    private SlideShowStatusEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(SlideShowStatusEnum demo: SlideShowStatusEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
