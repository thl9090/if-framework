package com.yx.common.jpush;


/**
 *  推送的消息类型及场景枚举
 *
 */
public enum MsgTypeEnum {
    MSG_TYPE1("1","根据参数中的url跳转到具体的详情页：比如活动详情页"),
    MSG_TYPE2("2","进入APP首页"),
    MSG_TYPE3("3","进入惠投专区"),
    MSG_TYPE4("4","进入转让专区"),
    MSG_TYPE5("5","进入借款页面"),
    MSG_TYPE6("6","进入账户中心"),
    MSG_TYPE7("7","进入风险测评"),
    MSG_TYPE8("8","进入存管账户"),
    MSG_TYPE9("9","进入优惠券"),
    MSG_TYPE10("10","进入绑定邮箱页面"),
    MSG_TYPE11("11","进入消息中心--我的消息"),
    MSG_TYPE12("12","进入消息中心--活动福利"),
    MSG_TYPE13("13","进入消息中心--新闻公告")
    ;


    /**
     * 成员变量
     */
    private String status;//状态值
    private String desc;//描述

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
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
    private MsgTypeEnum(String status, String desc) {
        this.status=status;
        this.desc=desc;
    }



}
