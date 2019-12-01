package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @ClassName 我的借款 Mdoel
 * @Author zhangxiaowen
 * @Date 2018/11/15 15:21
 * @Version 1.0
 **/
@ApiModel(value = "我的借款model")
public class MyBorrowExp implements Serializable {

    private static final long serialVersionUID = 1L;


    @Excel(name = "申请借款日期")
    private String applyDateStr;

    @Excel(name = "借款标题")
    private String productTitle;

    @Excel(name = "借款金额(元)")
    private String productAmountStr;

    @Excel(name = "参考年化收益(%)")
    private String yearRateStr;

    @Excel(name = "期限(天)")
    private String deadLine;

    @Excel(name = "下期还款本息(元)")
    private String nextRepayAmountStr;

    @Excel(name = "下期还款日")
    private String repayDateStr;

    @Excel(name = "借款总利息(元)")
    private String allInterestStr;

    @Excel(name = "结清日期")
    private String payOffDateStr;

    @Excel(name = "还款方式")
    private String repayModeStr;

    @Excel(name = "状态")
    private String productStatusStr;


    public String getApplyDateStr() {
        return applyDateStr;
    }

    public void setApplyDateStr(String applyDateStr) {
        this.applyDateStr = applyDateStr;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductAmountStr() {
        return productAmountStr;
    }

    public void setProductAmountStr(String productAmountStr) {
        this.productAmountStr = productAmountStr;
    }

    public String getYearRateStr() {
        return yearRateStr;
    }

    public void setYearRateStr(String yearRateStr) {
        this.yearRateStr = yearRateStr;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getNextRepayAmountStr() {
        return nextRepayAmountStr;
    }

    public void setNextRepayAmountStr(String nextRepayAmountStr) {
        this.nextRepayAmountStr = nextRepayAmountStr;
    }

    public String getRepayDateStr() {
        return repayDateStr;
    }

    public void setRepayDateStr(String repayDateStr) {
        this.repayDateStr = repayDateStr;
    }

    public String getAllInterestStr() {
        return allInterestStr;
    }

    public void setAllInterestStr(String allInterestStr) {
        this.allInterestStr = allInterestStr;
    }

    public String getPayOffDateStr() {
        return payOffDateStr;
    }

    public void setPayOffDateStr(String payOffDateStr) {
        this.payOffDateStr = payOffDateStr;
    }

    public String getRepayModeStr() {
        return repayModeStr;
    }

    public void setRepayModeStr(String repayModeStr) {
        this.repayModeStr = repayModeStr;
    }

    public String getProductStatusStr() {
        return productStatusStr;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr = productStatusStr;
    }
}
