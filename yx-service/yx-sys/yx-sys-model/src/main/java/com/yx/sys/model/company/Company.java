package com.yx.sys.model.company;

import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangxiaowen
 * @since 2018-08-08
 */
public class Company extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 客服热线
     */
	private String servicePhone;
    /**
     * 服务时间
     */
	private String serviceTime;
    /**
     * 地址
     */
	private String address;
    /**
     * 座机
     */
	private String telPhone;
    /**
     * 备案号
     */
	private String recordation;
    /**
     * 公司名
     */
	private String companyName;
    /**
     * 提示语
     */
	private String prompt;
    /**
     * 企业QQ
     */
	private String qq;
    /**
     * 公司简称
     */
	private String platform;
    /**
     * 域名
     */
	private String url;
	/**
	 * 公司章url
	 */
	private String msg;
    /**
     * 状态
     */
	private String state;

	/**
	 * 公司邮箱
	 */
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getRecordation() {
		return recordation;
	}

	public void setRecordation(String recordation) {
		this.recordation = recordation;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Company{" +
				"servicePhone='" + servicePhone + '\'' +
				", serviceTime='" + serviceTime + '\'' +
				", address='" + address + '\'' +
				", telPhone='" + telPhone + '\'' +
				", recordation='" + recordation + '\'' +
				", companyName='" + companyName + '\'' +
				", prompt='" + prompt + '\'' +
				", qq='" + qq + '\'' +
				", platform='" + platform + '\'' +
				", url='" + url + '\'' +
				", msg='" + msg + '\'' +
				", state='" + state + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
