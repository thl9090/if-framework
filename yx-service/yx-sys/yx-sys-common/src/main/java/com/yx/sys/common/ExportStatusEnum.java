package com.yx.sys.common;


/**
 * 导出状态
 * 所有的枚举定义都以enum结束
 */
public enum ExportStatusEnum {
    PROCESSING(0,"处理中"),
    SUCCESS(1,"已完成");

    /**
     * 成员变量
     */
    private int status;//状态值
    private String desc;//描述

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 构造方法
     * @param status
     * @param desc
     */
    private ExportStatusEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(ExportStatusEnum demo: ExportStatusEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
