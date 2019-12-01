package com.yx.sys.model.userMessageLog;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 消息日志表(站内信)
 * </p>
 *
 * @author zhangxiaowen
 * @since 2018-07-24
 */
@TableName("user_message_log")
public class UserMessageLog extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户
     */
	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;
    /**
     * 标题
     */
	@ApiModelProperty(value = "标题")
	@TableField("title")
	private String title;
    /**
     * 是否已读：0，否；1，是
     */
	@ApiModelProperty(value = "是否已读：0、否；1、是")
	@TableField("is_read")
	private Integer isRead;
    /**
     * 内容
     */
	@ApiModelProperty(value = "内容")
	@TableField("content")
	private String content;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "UserMessageLog{" +
				"userId=" + userId +
				", title='" + title + '\'' +
				", isRead=" + isRead +
				", content='" + content + '\'' +
				'}';
	}
}
