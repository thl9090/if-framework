package com.yx.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户信息
 *
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-19
 */
@TableName("user_info")
public class UserInfo extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码（用户注册时手机号码）")
    @TableField("phone")
	private Long phone;
    /**
     * 密码
     */
    @TableField("password")
	private String password;
    /**
     * 加密秘钥
     */
    @TableField("exkey")
	private String exkey;
    /**
     * 用户类型:1、个人用户；2、企业用户（我们平台自己定义的客户类型）
     */
    @ApiModelProperty(value="用户类型:1、个人用户；2、企业用户")
	@TableField("user_type")
	private Integer userType;

	/**
	 * 用户名
	 */
    @ApiModelProperty(value="用户名")
	@TableField("user_name")
	private String userName;

    /**
     * 真实姓名
     */
	@ApiModelProperty(value="真实姓名（客户名称）")
	@TableField("real_name")
	private String realName;
    /**
     * 证件类型：1、身份证号码;2、组织机构代码证
     */
	@ApiModelProperty(value="证件类型：1、身份证号码;2、组织机构代码证")
	@TableField("id_type")
	private Integer idType;
    /**
     * 证件号码
     */
	@ApiModelProperty(value="证件号码")
	@TableField("id_number")
	private String idNumber;

	/**
	 * 客户号码
	 * 当开户绑卡成功时，返回生成的客户号码
	 */
	@ApiModelProperty(value="客户号码")
	@TableField("cust_no")
	private String custNo;

	@ApiModelProperty(value="客户类型（客户类型是争对的鄂托克银行存管类型）:1、个人出借用户；2、个人融资用户，3、企业融资用户，4、企业担保用户")
	@TableField("cust_type")
	private Integer custType;

	@ApiModelProperty(value="客户注册手机号（用户开通存管时使用的手机号）")
	@TableField("register_phone")
	private Long registerPhone;
    /**
     * 银行账号
     */
	@ApiModelProperty(value="银行账号（存管银行账户号码）")
	@TableField("acno")
	private String acno;

	/**
	 * 银行名称
	 */
	@ApiModelProperty(value="银行名称")
	@TableField("bank_name")
	private String bankName;

	/**
	 * 绑定的银行卡号，脱敏处理
	 */
	@ApiModelProperty(value="银行卡号（绑定的银行卡号）")
	@TableField("card_no")
    private String cardNo;

	@ApiModelProperty(value="银行预留手机号")
	@TableField("bank_mobile")
	private String bankMobile;


	@ApiModelProperty(value="存管账户开通状态：0，未开通；1、已开通；2、被拒绝")
	@TableField("account_status")
	private Integer accountStatus;

    /**
     * 用户状态:1、有效；0、无效
     */
	@ApiModelProperty(value="用户状态:1、有效；0、无效")
	@TableField("status")
	private Integer status;
    /**
     * 企业名称
     */
	@ApiModelProperty(value="企业名称")
	@TableField("company_name")
	private String companyName;

    /**
     * 用户来源：1：PC;3：wx;4：ANDROID；5：IOS
     */
	@ApiModelProperty(value="用户来源：1：PC;3：wx;4：ANDROID；5：IOS")
	@TableField("source")
	private Integer source;

	@ApiModelProperty(value="推荐人手机号码")
	@TableField("ref_phone")
	private Long refPhone;

	@ApiModelProperty(value="邮箱")
	@TableField("email")
	private String email;

	@ApiModelProperty(value="是否新系统用户：0、表示老系统迁移过来的用户；1、表示新系统注册的用户")
	@TableField("is_new_user")
    private Integer isNewUser;


	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExkey() {
		return exkey;
	}

	public void setExkey(String exkey) {
		this.exkey = exkey;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Long getRefPhone() {
		return refPhone;
	}

	public void setRefPhone(Long refPhone) {
		this.refPhone = refPhone;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
			", phone=" + phone +
			", password=" + password +
			", exkey=" + exkey +
			", userType=" + userType +
			", realName=" + realName +
			", idType=" + idType +
			", idNumber=" + idNumber +
			", acno=" + acno +
			", status=" + status +
			", companyName=" + companyName +
			", source=" + source +
			",userName="+userName+
			"}";
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
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

	public Long getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(Long registerPhone) {
		this.registerPhone = registerPhone;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public Integer getCustType() {
		return custType;
	}

	public Integer getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(Integer isNewUser) {
		this.isNewUser = isNewUser;
	}
}
