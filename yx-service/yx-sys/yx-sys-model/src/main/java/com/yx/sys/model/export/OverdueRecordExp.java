package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName 逾期还款计划导出
 * @Author zhangxiaowen
 * @Date 2018/12/13 11:47
 * @Version 1.0
 **/
public class OverdueRecordExp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name="受托人用户名")
    private String borrowUserName;

    @Excel(name="受托人真实姓名")
    private String borrowRealName;

    @Excel(name="借款标题")
    private String title;

    @Excel(name="借款推荐方")
    private String guaranteeagencyName;

    @Excel(name="应还款时间")
    private String shouldRepayTime;

    @Excel(name="逾期天数")
    private String overdueDay;

    @Excel(name="应还总额")
    private BigDecimal amout;

    @Excel(name="应还本金")
    private BigDecimal capital;

    @Excel(name="应还利息")
    private BigDecimal interest;

    @Excel(name="罚息")
    private BigDecimal lateFee;

    @Excel(name="期次")
    private String planIndexCount;



    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getBorrowRealName() {
        return borrowRealName;
    }

    public void setBorrowRealName(String borrowRealName) {
        this.borrowRealName = borrowRealName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShouldRepayTime() {
        return shouldRepayTime;
    }

    public void setShouldRepayTime(String shouldRepayTime) {
        this.shouldRepayTime = shouldRepayTime;
    }

    public String getOverdueDay() {
        return overdueDay;
    }

    public void setOverdueDay(String overdueDay) {
        this.overdueDay = overdueDay;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public String getPlanIndexCount() {
        return planIndexCount;
    }

    public void setPlanIndexCount(String planIndexCount) {
        this.planIndexCount = planIndexCount;
    }

    public String getGuaranteeagencyName() {
        return guaranteeagencyName;
    }

    public void setGuaranteeagencyName(String guaranteeagencyName) {
        this.guaranteeagencyName = guaranteeagencyName;
    }
}
