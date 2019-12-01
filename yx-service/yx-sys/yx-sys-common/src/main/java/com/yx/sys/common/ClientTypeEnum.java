package com.yx.sys.common;


/**
 * slideshow 表中的type数据枚举类型
 *
 * @author lilulu
 * @since 2018-08-09
 */
public enum ClientTypeEnum {

    PC(1,"PC端"),
    WX(2,"微信端"),
    APP(3,"APP端");


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
    private ClientTypeEnum(Integer status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(Integer status) {
        for(ClientTypeEnum demo: ClientTypeEnum.values()) {
            if(demo.getStatus().equals(status)) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
