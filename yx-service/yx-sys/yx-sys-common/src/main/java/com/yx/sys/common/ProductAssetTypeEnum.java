package com.yx.sys.common;


/**
 * 标的借款类型枚举定义
 * @author  TangHuaLiang
 * @since 2018-08-09
 */
public enum ProductAssetTypeEnum {
    AN_XIANG(3,"安享系列","安享","安"),
    SI_XIANG(4,"私享系列","私享","私"),
    ZUN_XIANG(5,"尊享系列","尊享","尊"),
    FU_XIANG(6,"福享系列","福享","福"),
    XIN_SHOU(8,"新手福利","新手","新"),
    //JING(10,"净值标","净","净")        新系统去掉该标的类型by TangHuaLiang
    ;


    /**
     * 成员变量
     */
    private int status;//状态值
    private String desc;//全称
    private String shortName;//简称
    private String miniName;//缩写

    public int getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }
    public String getShortName() {
        return shortName;
    }

    public String getMiniName() {
        return miniName;
    }


    /**
     * 构造方法
     * @param status
     * @param desc
     */
    private ProductAssetTypeEnum(int status, String desc,String shortName,String miniName) {
        this.status=status;
        this.desc=desc;
        this.shortName=shortName;
        this.miniName=miniName;
    }

    public static String getDesc(int status) {
        for(ProductAssetTypeEnum demo: ProductAssetTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo.getDesc();
            }
        }
        return null;
    }

    public static ProductAssetTypeEnum resolve(Integer status) {
        if(status==null){
            return null;
        }
        for(ProductAssetTypeEnum demo: ProductAssetTypeEnum.values()) {
            if(demo.getStatus()==status) {
                return demo;
            }
        }
        return null;
    }




}
