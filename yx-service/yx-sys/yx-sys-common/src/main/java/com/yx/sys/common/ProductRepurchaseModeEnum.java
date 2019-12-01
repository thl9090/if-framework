package com.yx.sys.common;

/**
 * 标的还款方式枚举类
 *
 * @author YanBingHao
 * @since 2018/7/24
 */
public enum ProductRepurchaseModeEnum {

    DENG_E_BEN_XI(1, "等额本息"),
    XIAN_XI_HOU_BEN(2, "先息后本"),
    DAO_QI_HUAN_BEN_XI(3, "到期还本息"),
    ;

    private Integer id;
    private String msg;

    public Integer getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    ProductRepurchaseModeEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public static String getMsg(Integer id) {
        for(ProductRepurchaseModeEnum demo: ProductRepurchaseModeEnum.values()) {
            if(demo.getId().equals(id)) {
                return demo.getMsg();
            }
        }
        return null;
    }

    public static ProductRepurchaseModeEnum resolve(Integer id) {
        if(id==null){
            return null;
        }
        for(ProductRepurchaseModeEnum demo: ProductRepurchaseModeEnum.values()) {
            if(demo.getId().equals(id)) {
                return demo;
            }
        }
        return null;
    }

}
