package com.yx.common.core.enums;

/**
 * 是否枚举类
 *
 * @author YanBingHao
 * @since 2018/7/24
 */
public enum YesOrNoEnum {

    YES(1, "是"),
    NO(2, "否"),;

    private Integer id;
    private String msg;

    public Integer getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    YesOrNoEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }
}
