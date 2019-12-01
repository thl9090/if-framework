package com.yx.sys.common;

/**
 * sys_content表中的content_model_type字段描述
 *
 * @author lilulu
 * @since 2018/08/17 16:16
 */
public enum SysContentStatusEnum {

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
    private SysContentStatusEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(SysContentStatusEnum demo: SysContentStatusEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
