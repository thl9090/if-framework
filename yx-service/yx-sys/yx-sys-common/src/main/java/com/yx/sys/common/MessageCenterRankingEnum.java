package com.yx.sys.common;


/**
 * 枚举定义
 * lilulu
 * 20180816
 */
public enum MessageCenterRankingEnum {

    YES(0,"不置顶"),
	NO(1,"置顶");

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
    private MessageCenterRankingEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(MessageCenterRankingEnum demo: MessageCenterRankingEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
