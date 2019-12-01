package com.yx.code.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 用户手势密码表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-31
 */
@TableName("user_hand_pwd")
public class UserHandPwd extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 密码
     */
	private String password;
    /**
     * 加密密钥
     */
	private String exkey;
    /**
     * 是否启用：0、否；1、是
     */
	@TableField("is_used")
	private Integer isUsed;
	@TableField("fail_count")
	private Integer failCount;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	@Override
	public String toString() {
		return "UserHandPwd{" +
			", userId=" + userId +
			", password=" + password +
			", exkey=" + exkey +
			", isUsed=" + isUsed +
			", failCount=" + failCount +
			"}";
	}
}
