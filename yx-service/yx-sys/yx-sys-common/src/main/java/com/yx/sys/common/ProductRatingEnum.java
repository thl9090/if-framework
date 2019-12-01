package com.yx.sys.common;


/**
 * 标地评级枚举类
 * @author TangHuaLiang
 * @Since 2018-10-30
 */
public enum ProductRatingEnum {
    A(1,"A","A(100分以上)"),
    B(2,"B","B(90-99分)"),
    C(3,"C","C(80-89分)"),
    D(4,"D","D(70-79分)"),
    E(5,"E","E(70分以下)");
    ;

    /**
     * 成员变量
     */
    private int status;//状态值
    private String miniName;//缩写
    private String desc;//描述

    public int getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }
    public String getMiniName() {
        return miniName;
    }


    /**
     * 构造方法
     * @param status
     * @param desc
     */
    private ProductRatingEnum(int status,String miniName,String desc) {
        this.status=status;
        this.miniName=miniName;
        this.desc=desc;
    }

    public static String getDesc(int status) {
        for(ProductRatingEnum demo: ProductRatingEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

    public static ProductRatingEnum resolve(Integer status) {
        if(status==null){
            return null;
        }
        for(ProductRatingEnum demo: ProductRatingEnum.values()) {
            if(demo.getStatus()==status) {
                return demo;
            }
        }
        return null;
    }


}
