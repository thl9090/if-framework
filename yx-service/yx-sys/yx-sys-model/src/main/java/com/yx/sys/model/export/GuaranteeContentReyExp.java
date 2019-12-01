package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @ClassName 渠道待还信息导出
 * @Author zhangxiaowen
 * @Date 2018/8/27 20:18
 * @Version 1.0
 **/
public class GuaranteeContentReyExp implements Serializable {

    private static final long serialVersionUID = 1L;


    @Excel(name = "用户名")
    private String userName;

    @Excel(name = "姓名")
    private String userRealname;

    @Excel(name = "身份证号码")
    private String userIdno;

    @Excel(name = "借款标题")
    private String title;

    @Excel(name = "借款合同编号")
    private String borrowNo;

    @Excel(name = "发标金额")
    private String amount;

    @Excel(name = "实际招标金额")
    private String bidAmount;

    @Excel(name = "借款时间")
    private String borrowTime;

    @Excel(name = "期限")
    private String productDeadline;

    @Excel(name = "第几期")
    private String currentDeadline;

    @Excel(name = "年化收益率")
    private String profit;

    @Excel(name = "还款金额")
    private String payAmount;

    @Excel(name = "本金")
    private String capital;

    @Excel(name = "利息总金额")
    private String interest;

    @Excel(name = "还款利息/本期还款利息")
    private String monthInterest;

    @Excel(name = "满标时间")
    private String fullTime;

    @Excel(name = "提现时间")
    private String loanTime;

    @Excel(name = "还款时间")
    private String repayTime;

    @Excel(name = "还款方式")
    private String repayType;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getUserIdno() {
        return userIdno;
    }

    public void setUserIdno(String userIdno) {
        this.userIdno = userIdno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBorrowNo() {
        return borrowNo;
    }

    public void setBorrowNo(String borrowNo) {
        this.borrowNo = borrowNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getProductDeadline() {
        return productDeadline;
    }

    public void setProductDeadline(String productDeadline) {
        this.productDeadline = productDeadline;
    }

    public String getCurrentDeadline() {
        return currentDeadline;
    }

    public void setCurrentDeadline(String currentDeadline) {
        this.currentDeadline = currentDeadline;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getMonthInterest() {
        return monthInterest;
    }

    public void setMonthInterest(String monthInterest) {
        this.monthInterest = monthInterest;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }
}
