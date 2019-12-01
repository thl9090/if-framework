package com.yx.sys.common;


/**
 * 标的类型枚举定义
 * @author lilulu
 */
public enum MessageCenterEnum {

    NOTICE(1,"公告列表"),
    VIDEO(2,"视频专区"),
    INDUSTRY_INQUIRY(3,"行业资讯"),
    MEDIA(4,"媒体报道"),
    DEPOSIT(5,"存管动态"),
    P2P_INTELLECTUAL(6,"网贷知识"),
    POLICIE_REGULATIONS(7,"政策法规");

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
    private MessageCenterEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(MessageCenterEnum demo: MessageCenterEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
