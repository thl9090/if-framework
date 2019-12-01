package com.yx.code.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author TangHuaLiang
 * @since 2019-02-28
 */
@TableName("sys_email_log")
public class SysEmailLog extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
	private String subject;
    /**
     * 邮件内容
     */
	private String content;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SysEmailLog{" +
			", userId=" + userId +
			", subject=" + subject +
			", content=" + content +
			"}";
	}
}
