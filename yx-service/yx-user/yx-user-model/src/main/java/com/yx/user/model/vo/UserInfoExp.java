package com.yx.user.model.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @ClassName 导出用户列表信息
 * @Author lilulu
 * @Date 2018/10/24
 * @Version 1.0
 **/
public class UserInfoExp implements Serializable {

    @Excel(name = "手机号码")
	private Long phone;
    @Excel(name = "用户类型")
	private String userType;
	@Excel(name = "用户名")
	private String userName;
	@Excel(name = "邮箱")
	private String email;
    @Excel(name = "真实姓名")
	private String realName;
    @Excel(name = "证件类型")
	private String idType;
    @Excel(name = "证件号码")
	private String idNumber;
    @Excel(name = "存管账号")
	private String acno;
    /**
     * 用户状态:1、有效；0、无效
     */
    @Excel(name = "用户状态")
	private String status;

    @Excel(name = "存管账户状态")
	private String accountStatus;

	@Excel(name = "客户存管类型")
	private String custTypeStr;

    @Excel(name = "企业名称")
	private String companyName;
	
	@Excel(name = "推荐人手机号码")
	private Long refPhone;

	@Excel(name = "注册来源")
	private String sourceStr;

	@Excel(name = "注册时间")
	private String createTime;

	@Excel(name = "已开通授权")
	private String openAuthStr;

	@Excel(name = "客户号码")
	private String custNo;

	@Excel(name = "银行注册手机号码")
	private String registerPhone;

	@Excel(name = "银行名称")
	private String bankName;

	@Excel(name = "绑定银行卡号")
	private String cardNo;

	@Excel(name = "银行预留手机号")
	private String bankMobile;

	@Excel(name = "是否参与风险测评")
	private String isRisk;

	public Long getPhone() {
		return phone;
	}
	
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getIdType() {
		return idType;
	}
	
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public String getIdNumber() {
		return idNumber;
	}
	
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getAcno() {
		return acno;
	}
	
	public void setAcno(String acno) {
		this.acno = acno;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	

	public Long getRefPhone() {
		return refPhone;
	}
	
	public void setRefPhone(Long refPhone) {
		this.refPhone = refPhone;
	}

	public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public String getCustTypeStr() {
		return custTypeStr;
	}

	public void setCustTypeStr(String custTypeStr) {
		this.custTypeStr = custTypeStr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOpenAuthStr() {
		return openAuthStr;
	}

	public void setOpenAuthStr(String openAuthStr) {
		this.openAuthStr = openAuthStr;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public String getIsRisk() {
		return isRisk;
	}

	public void setIsRisk(String isRisk) {
		this.isRisk = isRisk;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
}
