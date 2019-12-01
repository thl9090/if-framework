package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @ClassName 待还还款计划导出
 * @Author zhangxiaowen
 * @Date 2018/12/14 11:47
 * @Version 1.0
 **/
public class RepaymentPendingPlanExp implements Serializable {

    private static final long serialVersionUID = 1L;


    @Excel(name = "借款标题")
    private String  title;

    @Excel(name = "还款日期")
    private String  repaymentDate;

    @Excel(name = "还款金额")
    private String repaymentAmount;

    @Excel(name = "本金")
    private String  repaymentCapital;

    @Excel(name = "利息")
    private String  repaymentInterest;

    @Excel(name = "期限")
    private String  repaymentDeadline;

    @Excel(name = "受托人姓名")
    private String  borrowUserName;

    @Excel(name = "借款推荐方")
    private String  guaranteeagencyName;

    @Excel(name = "受托人姓名")
    private String  borrowUserRealName;

    @Excel(name = "受托人手机号码")
    private String  borrowUserPhone;


    @Excel(name = "还款状态")
    private String  repaymentState;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getRepaymentCapital() {
        return repaymentCapital;
    }

    public void setRepaymentCapital(String repaymentCapital) {
        this.repaymentCapital = repaymentCapital;
    }

    public String getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(String repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
    }

    public String getRepaymentDeadline() {
        return repaymentDeadline;
    }

    public void setRepaymentDeadline(String repaymentDeadline) {
        this.repaymentDeadline = repaymentDeadline;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getGuaranteeagencyName() {
        return guaranteeagencyName;
    }

    public void setGuaranteeagencyName(String guaranteeagencyName) {
        this.guaranteeagencyName = guaranteeagencyName;
    }

    public String getBorrowUserRealName() {
        return borrowUserRealName;
    }

    public void setBorrowUserRealName(String borrowUserRealName) {
        this.borrowUserRealName = borrowUserRealName;
    }

    public String getBorrowUserPhone() {
        return borrowUserPhone;
    }

    public void setBorrowUserPhone(String borrowUserPhone) {
        this.borrowUserPhone = borrowUserPhone;
    }

    public String getRepaymentState() {
        return repaymentState;
    }

    public void setRepaymentState(String repaymentState) {
        this.repaymentState = repaymentState;
    }
}
