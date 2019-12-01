package com.yx.business.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * PC充值记录导出
 *
 * @author TangHuaLiang
 * @since 2018/7/20
 */
@ApiModel(value = "充值model")
public class RechargeExp implements Serializable {

    private static final long serialVersionUID = 5309702817929616119L;

    @Excel(name = "流水号")
    private String channelFlow;

    @Excel(name = "用户名")
    private String userName;

    @Excel(name = "用户姓名")
    private String userRealName;

    @Excel(name = "电话号码")
    private String phone;

    @Excel(name = "充值时间")
    private String orderDateStr;

    @Excel(name = "充值金额")
    private String rechargeAmount;

    @Excel(name = "充值类型")
    private String orderTypeStr;

    @Excel(name = "状态")
    private String orderStatusStr;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChannelFlow() {
        return channelFlow;
    }

    public void setChannelFlow(String channelFlow) {
        this.channelFlow = channelFlow;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getOrderDateStr() {
        return orderDateStr;
    }

    public void setOrderDateStr(String orderDateStr) {
        this.orderDateStr = orderDateStr;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getOrderTypeStr() {
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }
}
