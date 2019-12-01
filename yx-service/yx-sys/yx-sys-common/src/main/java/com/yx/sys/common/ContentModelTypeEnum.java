package com.yx.sys.common;

/**
 * sys_content表中的content_model_type字段描述
 *
 * @author lilulu
 * @since 2018/08/17 16:16
 */
public enum ContentModelTypeEnum {

    AGREEMENT(1,"协议"),
    DEVELOPING_COURSE(2,"发展历程"),
    COMMON_PROBLEM(3,"常见问题"),
    RECRUITMENT(4,"招贤纳士")
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
    private ContentModelTypeEnum(int status, String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(ContentModelTypeEnum demo: ContentModelTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
