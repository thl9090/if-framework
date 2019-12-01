package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @ClassName 渠道已还清数据导出
 * @Author zhangxiaowen
 * @Date 2018/8/28 9:15
 * @Version 1.0
 **/
public class GuaranteeContentFinishExp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "用户名")
    private String userName;

    @Excel(name = "姓名")
    private String userRealname;

    @Excel(name = "身份证号码")
    private String userIdno;

    @Excel(name = "借款标题")
    private String title;


    @Excel(name = "借款金额")
    private String amount;

    @Excel(name = "实际招标金额")
    private String bidAmount;

    @Excel(name = "借款时间")
    private String borrowTime;

    @Excel(name = "期限")
    private String productDeadline;

    @Excel(name = "满标时间")
    private String fullTime;

    @Excel(name = "应还款时间")
    private String payTime;

    @Excel(name = "还款金额")
    private String payAllAmount;

    @Excel(name = "实际还款时间")
    private String realityTime;

    @Excel(name = "是否逾期")
    private String isOverdue;

    @Excel(name = "罚息金额")
    private String defaultAmount;

    @Excel(name = "还款状态")
    private String rayType;


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

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayAllAmount() {
        return payAllAmount;
    }

    public void setPayAllAmount(String payAllAmount) {
        this.payAllAmount = payAllAmount;
    }

    public String getRealityTime() {
        return realityTime;
    }

    public void setRealityTime(String realityTime) {
        this.realityTime = realityTime;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }

    public String getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(String defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public String getRayType() {
        return rayType;
    }

    public void setRayType(String rayType) {
        this.rayType = rayType;
    }
}
