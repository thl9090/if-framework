package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 发送短信记录表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-19
 */
@TableName("sys_sms_log")
public class SysSmsLog extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 接收短信用户id
     */
    @TableField("user_id")
	private Long userId;
    /**
     * 手机号
     */
	@TableField("phone")
	private Long phone;
    /**
     * 业务id
     */
    @TableField("bus_id")
	private Long busId;
    /**
     * 验证码
     */
	@TableField("vcode")
	private Long vcode;
    /**
     * 类型：1：验证码类型，2：发标类型，3：还款类型，4：收益类型，5：逾期类型,6:垫付类型
     */
	@TableField("type")
	private Integer type;
    /**
     * 短信内容
     */
	@TableField("message")
	private String message;

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public Long getVcode() {
		return vcode;
	}

	public void setVcode(Long vcode) {
		this.vcode = vcode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SysSmsLog{" +
			", userId=" + userId +
			", phone=" + phone +
			", vcode=" + vcode +
			", type=" + type +
			", message=" + message +
			"}";
	}
}
