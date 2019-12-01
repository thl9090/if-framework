package com.yx.user.model.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @ClassName 导出用户开户列表信息
 * @Author TangHuaLiang
 * @Date 2019/02/26
 * @Version 1.0
 **/
public class UserOpenAccountExp implements Serializable {

	@Excel(name = "业务流水号")
	private String channelFlow;
    @Excel(name = "手机号码")
	private Long phone;
	@Excel(name = "用户名")
	private String userName;
    @Excel(name = "真实姓名")
	private String realName;
	@Excel(name = "开户行名称")
	private String bankName;
	@Excel(name = "银行卡号")
	private String cardNo;
	@Excel(name = "绑卡费用（元）")
	private String fee="1.00";
	@Excel(name = "开户时间")
	private String updateTime;

	public Long getPhone() {
		return phone;
	}
	
	public void setPhone(Long phone) {
		this.phone = phone;
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

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getChannelFlow() {
		return channelFlow;
	}

	public void setChannelFlow(String channelFlow) {
		this.channelFlow = channelFlow;
	}
}
