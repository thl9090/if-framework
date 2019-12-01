package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @ClassName 电子账单交易记录Model
 * @Author zhangxiaowen
 * @Date 2018/11/16 9:02
 * @Version 1.0
 **/
@ApiModel(value = "电子账单交易记录Model")
public class BillTradesRecordExp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "流水号")
    private String channelFlow;

    @Excel(name = "日期")
    private String createTime;

    @Excel(name = "交易类型")
    private String transTypeName;

    @Excel(name = "金额")
    private String amount;

    @Excel(name = "流水类型")
    private String flowTypeStr;

    @Excel(name = "状态")
    private String statusStr;


    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public String getChannelFlow() {
        return channelFlow;
    }

    public void setChannelFlow(String channelFlow) {
        this.channelFlow = channelFlow;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFlowTypeStr() {
        return flowTypeStr;
    }

    public void setFlowTypeStr(String flowTypeStr) {
        this.flowTypeStr = flowTypeStr;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }


}
