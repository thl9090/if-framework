package com.yx.sys.model.vo;

import com.yx.sys.model.userMessageLog.UserMessageLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 消息日志表(站内信)--扩展类
 * </p>
 *
 * @author zhangxiaowen
 * @since 2018-07-24
 */
@ApiModel(value="UserMessageLogVO",description="用户消息扩展类")
public class UserMessageLogVO extends UserMessageLog {

	private Integer currentPage;

	@ApiModelProperty(value="用户名")
	private String userName;

	@ApiModelProperty(value="真实姓名")
	private String realName;

	private String createTimeStr;



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

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}
