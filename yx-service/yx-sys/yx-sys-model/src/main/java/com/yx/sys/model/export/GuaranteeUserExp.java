package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @ClassName 渠道用户基础数据导出
 * @Author zhangxiaowen
 * @Date 2018/8/27 13:53
 * @Version 1.0
 **/
public class GuaranteeUserExp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "用户名")
    private String userName;

    @Excel(name = "用户名设置")
    private String userNameUp;

    @Excel(name = "登录密码设置")
    private String userPasswordUp;

    @Excel(name = "注册时间")
    private String crerateTime;

    @Excel(name = "姓名")
    private String userRealname;

    @Excel(name = "身份证号码")
    private String userIdno;

    @Excel(name = "绑定手机号码")
    private String phone;

    @Excel(name = "是否绑定手机")
    private String isPphone;

    @Excel(name = "邮箱账号")
    private String email;

    @Excel(name = "是否绑定邮箱")
    private String isEmail;

    @Excel(name = "存管专户")
    private String loanId;

    @Excel(name = "是否开通")
    private String isLoanId;

    @Excel(name = "绑定银行卡卡号")
    private String idCard;

    @Excel(name = "所属银行")
    private String bankName;

    @Excel(name = "绑定的渠道")
    private String guarantee;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameUp() {
        return userNameUp;
    }

    public void setUserNameUp(String userNameUp) {
        this.userNameUp = userNameUp;
    }

    public String getUserPasswordUp() {
        return userPasswordUp;
    }

    public void setUserPasswordUp(String userPasswordUp) {
        this.userPasswordUp = userPasswordUp;
    }

    public String getCrerateTime() {
        return crerateTime;
    }

    public void setCrerateTime(String crerateTime) {
        this.crerateTime = crerateTime;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsPphone() {
        return isPphone;
    }

    public void setIsPphone(String isPphone) {
        this.isPphone = isPphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(String isEmail) {
        this.isEmail = isEmail;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getIsLoanId() {
        return isLoanId;
    }

    public void setIsLoanId(String isLoanId) {
        this.isLoanId = isLoanId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }
}
