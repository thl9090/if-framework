package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 邮件发送记录导出实体类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2019-02-28
 */
public class SysEmailLogExp implements Serializable {

	@Excel(name ="用户名")
	private String userName;

	@Excel(name ="手机号")
	private Long phone;

	@Excel(name ="用户类型")
	private String userTypeStr;

	@Excel(name ="客户类型")
	private String custTypeStr;

	@Excel(name ="用户状态")
	private String userStatusStr;

	@Excel(name ="激活状态")
	private String statusStr;

	@Excel(name ="绑定邮箱")
	private String email;

	@Excel(name ="邮件发送时间",format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@Excel(name ="邮件激活时间",format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getUserTypeStr() {
		return userTypeStr;
	}

	public void setUserTypeStr(String userTypeStr) {
		this.userTypeStr = userTypeStr;
	}

	public String getCustTypeStr() {
		return custTypeStr;
	}

	public void setCustTypeStr(String custTypeStr) {
		this.custTypeStr = custTypeStr;
	}

	public String getUserStatusStr() {
		return userStatusStr;
	}

	public void setUserStatusStr(String userStatusStr) {
		this.userStatusStr = userStatusStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
