package com.yx.business.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @ClassName PC提现记录导出
 * @Author zhangxiaowen
 * @Date 2018/11/7 14:57
 * @Version 1.0
 **/
@ApiModel(value = "提现model")
public class WithdrawExp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "流水号")
    private String channelFlow;

    @Excel(name = "用户名")
    private String userName;

    @Excel(name = "用户姓名")
    private String userRealName;

    @Excel(name = "提现金额")
    private String amountStr;

    @Excel(name = "手续费金额")
    private String feeAmountStr;

    @Excel(name = "到账金额")
    private String realAmountStr;

    @Excel(name = "提现时间")
    private String orderDateStr;

    @Excel(name = "提现类型")
    private String orderTypeStr;


    @Excel(name = "状态")
    private String orderStatusStr;


    public String getChannelFlow() {
        return channelFlow;
    }

    public void setChannelFlow(String channelFlow) {
        this.channelFlow = channelFlow;
    }

    public String getOrderDateStr() {
        return orderDateStr;
    }

    public void setOrderDateStr(String orderDateStr) {
        this.orderDateStr = orderDateStr;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public String getFeeAmountStr() {
        return feeAmountStr;
    }

    public void setFeeAmountStr(String feeAmountStr) {
        this.feeAmountStr = feeAmountStr;
    }

    public String getRealAmountStr() {
        return realAmountStr;
    }

    public void setRealAmountStr(String realAmountStr) {
        this.realAmountStr = realAmountStr;
    }

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
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

    public String getOrderTypeStr() {
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }
}
