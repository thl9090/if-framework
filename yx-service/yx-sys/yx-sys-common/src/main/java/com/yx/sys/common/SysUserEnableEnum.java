package com.yx.sys.common;


/**
 * 系统用户是否可用
 * 所有的枚举定义都以enum结束
 */
public enum SysUserEnableEnum {
    YES(1,"是"),
    NO(0,"否");

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
    private SysUserEnableEnum(Integer status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(Integer status) {
        for(SysUserEnableEnum demo: SysUserEnableEnum.values()) {
            if(demo.getStatus().equals(status)) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
