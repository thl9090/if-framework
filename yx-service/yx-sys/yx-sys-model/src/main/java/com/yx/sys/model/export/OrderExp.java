package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 投标记录表
 * </p>
 *
 * @author ZhangPanPan
 * @since 2018-08-20
 */
public class OrderExp implements Serializable {

	@Excel(name ="借款标题")
	private String title;

	@Excel(name ="利息")
	private BigDecimal interest;

	@Excel(name ="本金")
	private BigDecimal capital;

	@Excel(name ="总期数")
	private String plans;

	@Excel(name ="借款人")
	private String userName;

	@Excel(name ="借款人姓名")
	private String realName;

	@Excel(name ="还款状态")
	private String productStatusStr;

	@Excel(name ="保障机构名")
	private String guaranteeName;

	@Excel(name ="还款日期")
	private String receiptTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public String getPlans() {
		return plans;
	}

	public void setPlans(String plans) {
		this.plans = plans;
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

	public String getProductStatusStr() {
		return productStatusStr;
	}

	public void setProductStatusStr(String productStatusStr) {
		this.productStatusStr = productStatusStr;
	}

	public String getGuaranteeName() {
		return guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}

}
