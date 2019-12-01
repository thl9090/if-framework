package com.yx.sys.common;


/**
 * slideshow 表中的type数据枚举类型
 *
 * @author lilulu
 * @since 2018-08-09
 */
public enum SlideshowTypeEnum {

    CAROUSEL(1,"轮播图"),
    ADVERTISING(2,"开屏广告"),
    ACTIVITY_MSG(3,"活动消息"),
    ;

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
    private SlideshowTypeEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(SlideshowTypeEnum demo: SlideshowTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
