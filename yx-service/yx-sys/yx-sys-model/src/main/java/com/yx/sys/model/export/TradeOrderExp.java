package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值统计
 *
 * @author lilulu
 * @since 2018-08-29
 */
public class TradeOrderExp implements Serializable {

	private static final long serialVersionUID = 1L;

	@Excel(name = "流水单号")
	private String channelFlow;

	@Excel(name = "用户名")
	private String userName;

	@Excel(name = "姓名")
	private String realName;

	@Excel(name = "金额")
	private BigDecimal orderAmount;

	@Excel(name = "创建时间",format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@Excel(name = "状态")
	private String orderStatusStr;

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getChannelFlow() {
		return channelFlow;
	}

	public void setChannelFlow(String channelFlow) {
		this.channelFlow = channelFlow;
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


	public String getOrderStatusStr() {
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
