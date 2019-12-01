package com.yx.user.model.vo;

import com.yx.user.model.UserInfo;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class UserInfoVO extends UserInfo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 验证码id
	 */
	@ApiModelProperty(value="验证码id")
	private String captchaId;

	/**
	 * 验证码
	 */
	@ApiModelProperty(value="图形验证码")
	private String captcha;
	/**
	 * 短信验证码
	 */
	@ApiModelProperty(value="短信验证码")
	private String smsCode;

	@ApiModelProperty(value="原密码")
	private String oldPassword;

	@ApiModelProperty(value="确认密码")
	private String comfirmPassword;

	private String returnUrl;

	@ApiModelProperty(value="交易金额")
	private BigDecimal amount;

	private Date createTimeStart;

	private Date createTimeEnd;

	@ApiModelProperty(value="是否开启了手势密码， 1：是；0：否；")
    private Integer isUsedHandPwd;

	/**
	 * 回调通知路径
	 */
	private String callbackUrl;

	/**
	 * 成功跳转路径
	 */
	private String responsePath;

	private Long newRegisterPhone;

	@ApiModelProperty(value = "accessToken")
	private String accessToken;

	private Integer channelType;

	@ApiModelProperty(value="已开通授权")
	private String openAuthStr;

	@ApiModelProperty(value="手机号*缩写")
	private String phoneStr;
	@ApiModelProperty(value="邮箱*缩写")
	private String emailStr;
	@ApiModelProperty(value="证件号码*缩写")
	private String idNumberStr;
	@ApiModelProperty(value="银行卡号后4位")
	private String cardNoStr;
	@ApiModelProperty(value="银行预留手机号*缩写")
	private String bankMobileStr;
	@ApiModelProperty(value="企业名称*缩写")
	private String companyNameStr;
	@ApiModelProperty(value="是否参与风险测评：1、是；0、否")
	private Integer isRisk;
	@ApiModelProperty(value="最近登陆时间")
	private String loginTimeStr;

	private String registerPhoneStr;

	private String channelFlow;
	private String fee;

	/**
	 * 是否显示法大大菜单栏：1、显示；0、不显示
	 */
	private Integer showFddMenu;
	/**
	 * 法大大认证菜单是否可点击：1、可以；0、不可以
	 */
	private Integer fddStatus;
	/**
	 * 认证状态文字说明：未认证，已认证，认证中，认证失败
	 */
	private String fddStatusStr;

	/**
	 * 是否需要法大大弹框提示：1、提示去认证；0、不提示
	 */
	private Integer fddTip;

	/**
	 * 法大大弹框内容
	 */
	private String fddTipContent;

    public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}


	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getComfirmPassword() {
		return comfirmPassword;
	}

	public void setComfirmPassword(String comfirmPassword) {
		this.comfirmPassword = comfirmPassword;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Integer getIsUsedHandPwd() {
		return isUsedHandPwd;
	}

	public void setIsUsedHandPwd(Integer isUsedHandPwd) {
		this.isUsedHandPwd = isUsedHandPwd;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getResponsePath() {
		return responsePath;
	}

	public void setResponsePath(String responsePath) {
		this.responsePath = responsePath;
	}

	public Long getNewRegisterPhone() {
		return newRegisterPhone;
	}

	public void setNewRegisterPhone(Long newRegisterPhone) {
		this.newRegisterPhone = newRegisterPhone;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getOpenAuthStr() {
		return openAuthStr;
	}

	public void setOpenAuthStr(String openAuthStr) {
		this.openAuthStr = openAuthStr;
	}

	public String getPhoneStr() {
		return phoneStr;
	}

	public void setPhoneStr(String phoneStr) {
		this.phoneStr = phoneStr;
	}

	public String getIdNumberStr() {
		return idNumberStr;
	}

	public void setIdNumberStr(String idNumberStr) {
		this.idNumberStr = idNumberStr;
	}

	public String getCardNoStr() {
		return cardNoStr;
	}

	public void setCardNoStr(String cardNoStr) {
		this.cardNoStr = cardNoStr;
	}

	public String getBankMobileStr() {
		return bankMobileStr;
	}

	public void setBankMobileStr(String bankMobileStr) {
		this.bankMobileStr = bankMobileStr;
	}

	public String getEmailStr() {
		return emailStr;
	}

	public void setEmailStr(String emailStr) {
		this.emailStr = emailStr;
	}

	public String getCompanyNameStr() {
		return companyNameStr;
	}

	public void setCompanyNameStr(String companyNameStr) {
		this.companyNameStr = companyNameStr;
	}

	public Integer getIsRisk() {
		return isRisk;
	}

	public void setIsRisk(Integer isRisk) {
		this.isRisk = isRisk;
	}

	public String getLoginTimeStr() {
		return loginTimeStr;
	}

	public void setLoginTimeStr(String loginTimeStr) {
		this.loginTimeStr = loginTimeStr;
	}

	public String getRegisterPhoneStr() {
		return registerPhoneStr;
	}

	public void setRegisterPhoneStr(String registerPhoneStr) {
		this.registerPhoneStr = registerPhoneStr;
	}

	public String getChannelFlow() {
		return channelFlow;
	}

	public void setChannelFlow(String channelFlow) {
		this.channelFlow = channelFlow;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public Integer getFddStatus() {
		return fddStatus;
	}

	public void setFddStatus(Integer fddStatus) {
		this.fddStatus = fddStatus;
	}

	public String getFddStatusStr() {
		return fddStatusStr;
	}

	public void setFddStatusStr(String fddStatusStr) {
		this.fddStatusStr = fddStatusStr;
	}

	public Integer getFddTip() {
		return fddTip;
	}

	public void setFddTip(Integer fddTip) {
		this.fddTip = fddTip;
	}

	public Integer getShowFddMenu() {
		return showFddMenu;
	}

	public void setShowFddMenu(Integer showFddMenu) {
		this.showFddMenu = showFddMenu;
	}

	public String getFddTipContent() {
		return fddTipContent;
	}

	public void setFddTipContent(String fddTipContent) {
		this.fddTipContent = fddTipContent;
	}
}
