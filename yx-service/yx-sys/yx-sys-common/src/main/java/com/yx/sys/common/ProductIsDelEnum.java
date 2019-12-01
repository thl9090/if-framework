package com.yx.sys.common;

/**
 * 标的是否被删除
 * @author TangHuaLiang
 * @since 2018/9/10
 */
public enum ProductIsDelEnum {

    NO_DEL(0,"有效"),
    DELETE(1,"无效");


    private Integer status;
    private String desc;




    public Integer getStatus() {
        return status;
    }


    public String getDesc() {
        return desc;
    }


    private ProductIsDelEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for(ProductIsDelEnum demo: ProductIsDelEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }


}
