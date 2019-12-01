package com.yx.sys.common;

/**
 * 借款用途枚举
 * @author TangHuaLiang
 * @since 2018-08-07
 */
public enum ProductUseTypeEnum {

    PERSONAL(1,"个人消费"),//已弃用
    SHORT_TIME(2,"短期周转"),//已弃用
    COMPANY_MONEY(3,"企业资金周转"),//已弃用
    OTHER(4,"其它用途"),
    HOUSE(5,"赎楼"),//已弃用
    COMPANY_BRIDGE(6,"企业过桥"),//已弃用
    CAR_BUYING(7,"购车"),
    RENOVATION(8,"装修"),
    EDUCATION_TRANINING(9,"教育培训"),
    PERSONAL_TOURISM(10,"个人旅游"),
    COMPANIE_OPERATION(11,"企业经营"),
    DAILY_CONSUMPTION(12,"日常消费");
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
    private ProductUseTypeEnum(int status,String desc) {
        this.status=status;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(ProductUseTypeEnum demo:ProductUseTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

}
