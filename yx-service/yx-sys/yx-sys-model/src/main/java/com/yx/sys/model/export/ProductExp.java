package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 标的信息导出实体类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-30
 */
public class ProductExp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "标的编号")
    private String productNo;

    @Excel(name = "标的名称")
    private String title;

    @Excel(name = "借款类型")
    private String assetTypeName;

    @Excel(name = "受托人")
    private String borrowUserName;

    @Excel(name = "受托人手机号")
    private Long borrowUserPhone;

    @Excel(name = "保障机构")
    private String guaranteeName;

    @Excel(name = "借款用途")
    private String useTypeName;

    @Excel(name = "是否参与活动")
    private String isActivityStr;

    @Excel(name = "是否企业借款")
    private String isCompanyStr;

    @Excel(name = "还款方式")
    private String repurchaseModeName;

    @Excel(name = "是否可转让")
    private String isTransferStr;

    @Excel(name = "借款金额")
    private BigDecimal productAmount;

    @Excel(name = "借款期限")
    private Integer productDeadline;

    @Excel(name = "年收益")
    private BigDecimal profit;

    @Excel(name = "借款人服务费率")
    private BigDecimal serviceFee;

    @Excel(name = "截标金额")
    private BigDecimal stopBidAmount;

    @Excel(name = "截标时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date stopBidTime;

    @Excel(name = "投资人服务费率")
    private BigDecimal bidServerFee;

    @Excel(name = "招标天数")
    private Integer timeCount;

    @Excel(name = "最小投资金额")
    private BigDecimal minBidAmount;

    @Excel(name = "最大投资金额")
    private BigDecimal maxBidAmount;

    @Excel(name = "借款描述")
    private String borrowDesc;

    @Excel(name = "反担保")
    private String guaranteeDesc;

    @Excel(name = "风控描述")
    private String riskDesc;

    @Excel(name = "标的状态")
    private String productStatusName;

    @Excel(name = "标的类型")
    private String productTypeName;

    @Excel(name = "投标金额")
    private BigDecimal bidAmount;

    @Excel(name = "招标开始时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date investBeginDate;

    @Excel(name = "招标结束时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date investEndDate;

    @Excel(name = "满标时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date fullBidDate;

    @Excel(name = "加息")
    private BigDecimal addProfit;



    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public String getGuaranteeName() {
        return guaranteeName;
    }

    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }

    public String getUseTypeName() {
        return useTypeName;
    }

    public void setUseTypeName(String useTypeName) {
        this.useTypeName = useTypeName;
    }

    public String getIsActivityStr() {
        return isActivityStr;
    }

    public void setIsActivityStr(String isActivityStr) {
        this.isActivityStr = isActivityStr;
    }

    public String getIsCompanyStr() {
        return isCompanyStr;
    }

    public void setIsCompanyStr(String isCompanyStr) {
        this.isCompanyStr = isCompanyStr;
    }

    public String getRepurchaseModeName() {
        return repurchaseModeName;
    }

    public void setRepurchaseModeName(String repurchaseModeName) {
        this.repurchaseModeName = repurchaseModeName;
    }

    public String getIsTransferStr() {
        return isTransferStr;
    }

    public void setIsTransferStr(String isTransferStr) {
        this.isTransferStr = isTransferStr;
    }

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    public Integer getProductDeadline() {
        return productDeadline;
    }

    public void setProductDeadline(Integer productDeadline) {
        this.productDeadline = productDeadline;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getStopBidAmount() {
        return stopBidAmount;
    }

    public void setStopBidAmount(BigDecimal stopBidAmount) {
        this.stopBidAmount = stopBidAmount;
    }

    public Date getStopBidTime() {
        return stopBidTime;
    }

    public void setStopBidTime(Date stopBidTime) {
        this.stopBidTime = stopBidTime;
    }

    public BigDecimal getBidServerFee() {
        return bidServerFee;
    }

    public void setBidServerFee(BigDecimal bidServerFee) {
        this.bidServerFee = bidServerFee;
    }

    public Integer getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(Integer timeCount) {
        this.timeCount = timeCount;
    }

    public BigDecimal getMinBidAmount() {
        return minBidAmount;
    }

    public void setMinBidAmount(BigDecimal minBidAmount) {
        this.minBidAmount = minBidAmount;
    }

    public BigDecimal getMaxBidAmount() {
        return maxBidAmount;
    }

    public void setMaxBidAmount(BigDecimal maxBidAmount) {
        this.maxBidAmount = maxBidAmount;
    }

    public String getBorrowDesc() {
        return borrowDesc;
    }

    public void setBorrowDesc(String borrowDesc) {
        this.borrowDesc = borrowDesc;
    }

    public String getGuaranteeDesc() {
        return guaranteeDesc;
    }

    public void setGuaranteeDesc(String guaranteeDesc) {
        this.guaranteeDesc = guaranteeDesc;
    }

    public String getRiskDesc() {
        return riskDesc;
    }

    public void setRiskDesc(String riskDesc) {
        this.riskDesc = riskDesc;
    }

    public String getProductStatusName() {
        return productStatusName;
    }

    public void setProductStatusName(String productStatusName) {
        this.productStatusName = productStatusName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Date getInvestBeginDate() {
        return investBeginDate;
    }

    public void setInvestBeginDate(Date investBeginDate) {
        this.investBeginDate = investBeginDate;
    }

    public Date getInvestEndDate() {
        return investEndDate;
    }

    public void setInvestEndDate(Date investEndDate) {
        this.investEndDate = investEndDate;
    }

    public Date getFullBidDate() {
        return fullBidDate;
    }

    public void setFullBidDate(Date fullBidDate) {
        this.fullBidDate = fullBidDate;
    }

    public BigDecimal getAddProfit() {
        return addProfit;
    }

    public void setAddProfit(BigDecimal addProfit) {
        this.addProfit = addProfit;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public Long getBorrowUserPhone() {
        return borrowUserPhone;
    }

    public void setBorrowUserPhone(Long borrowUserPhone) {
        this.borrowUserPhone = borrowUserPhone;
    }
}
