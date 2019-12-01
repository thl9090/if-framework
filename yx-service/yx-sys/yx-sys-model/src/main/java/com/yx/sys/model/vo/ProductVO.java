package com.yx.sys.model.vo;

import com.yx.sys.model.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 标的扩展类
 *
 * @author TangHuaLiang
 * @since 2018-07-30
 */
@ApiModel(value = "标的扩展类", description = "标的扩展类")
public class ProductVO extends Product {


    private Integer currentPage;
    /**
     * 审核状态
     */

    private Integer checkStatus;

    /**
     * 初审备注
     */
    private String firstCheckRemark;

    /**
     * 终审状态
     */
    private String finalCheckRemark;

    /**
     * 文件列表
     */
    private List<String> fileList;

    /**
     * 标的状态列表
     */
    private List<Integer> productStatusList;

    /**
     * 剩余可投金额
     */
    @ApiModelProperty(value = "剩余可投金额")
    private BigDecimal restBidAmount;

    @ApiModelProperty(value = "标地状态（中文）")
    private String productStatusDesc;

    private String restBidAmountStr;

    private String profitStr;

    private String addProfitStr;

    private String productAmountStr;

    @ApiModelProperty(value = "最小可投金额")
    private String minBidAmountStr;

    @ApiModelProperty(value = "受托人手机号码")
    private Long borrowUserPhone;

    @ApiModelProperty(value = "受托人用户名")
    private String borrowUserName;

    @ApiModelProperty(value = "受托人真实姓名")
    private String borrowUserRealName;

    private String createTimeStr;

    private Date createTimeStart;

    private Date createTimeEnd;

    @ApiModelProperty(value = "保障机构名称")
    private String guaranteeName;

    @ApiModelProperty(value = "还款方式描述")
    private String repurchaseModeDesc;

    @ApiModelProperty(value = "招标结束时间(时间戳)")
    private Long investEndDateTime;

    @ApiModelProperty(value = "原始借款人id(逗号分隔)")
    private String oriBorrowerIds;
    @ApiModelProperty(value = "原始借款人姓名(逗号分隔)")
    private String oriBorrowers;
    @ApiModelProperty(value = "借款类型简写")
    private String assetTypeMiniName;
    @ApiModelProperty(value = "折价率")
    private String rateOfDiscount;
    private BigDecimal transferTotalAmount;
    @ApiModelProperty(value = "债权价值")
    private String transferTotalAmountStr;
    @ApiModelProperty(value = "下期还款日期")
    private String recentRepaymentDateStr;
    @ApiModelProperty(value = "预计收益")
    private String expectProfitStr;
    @ApiModelProperty(value = "原标id(债转标会用到)")
    private Long oldProductId;

    @ApiModelProperty(value = "已投百分比")
    private String bidPercent;

    //以下字段用于PC端条件查询
    /**
     * 标地状态查询类型
     */
    private Integer productStatusSearchType;
    /**
     * 标的金额查询类型
     */
    private Integer amountSearchType;

    /**
     * 借款期限查询类型
     */
    private Integer productDeadlineSearchType;

    /**
     * 收益查询类型
     */
    private Integer profitSearchType;

    //借款开始金额
    private BigDecimal productAmountStart;

    //借款结束金额
    private BigDecimal productAmountEnd;

    //借款期限开始
    private Integer productDeadlineStart;

    //借款期限结束
    private Integer ProductDeadlineEnd;

    //收益开始区间
    private BigDecimal profitStart;

    //收益结束区间
    private BigDecimal profitEnd;

    /**
     * 初审时间
     */
    private Date firstCheckTime;

    /**
     * 终审时间
     */
    private Date finalCheckTime;


    @ApiModelProperty("标的期限")
    private String productDeadlineStr;


    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @ApiModelProperty(value = "验证码id")
    private String captchaId;

    @ApiModelProperty(value = "处理中的出借订单数量")
    private Integer processBidOrderCount;

    @ApiModelProperty(value = "标的状态（中文）")
    private String productStatusStr;

    private Long smartBidPlanId;

    private String smartName;

    public Integer getProcessBidOrderCount() {
        return processBidOrderCount;
    }

    public void setProcessBidOrderCount(Integer processBidOrderCount) {
        this.processBidOrderCount = processBidOrderCount;
    }

    public String getProductDeadlineStr() {
        return productDeadlineStr;
    }

    public void setProductDeadlineStr(String productDeadlineStr) {
        this.productDeadlineStr = productDeadlineStr;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }


    public String getFirstCheckRemark() {
        return firstCheckRemark;
    }

    public void setFirstCheckRemark(String firstCheckRemark) {
        this.firstCheckRemark = firstCheckRemark;
    }

    public String getFinalCheckRemark() {
        return finalCheckRemark;
    }

    public void setFinalCheckRemark(String finalCheckRemark) {
        this.finalCheckRemark = finalCheckRemark;
    }

    public List<Integer> getProductStatusList() {
        return productStatusList;
    }

    public void setProductStatusList(List<Integer> productStatusList) {
        this.productStatusList = productStatusList;
    }

    public BigDecimal getRestBidAmount() {
        return restBidAmount;
    }

    public void setRestBidAmount(BigDecimal restBidAmount) {
        this.restBidAmount = restBidAmount;
    }

    public String getProfitStr() {
        return profitStr;
    }

    public void setProfitStr(String profitStr) {
        this.profitStr = profitStr;
    }

    public String getMinBidAmountStr() {
        return minBidAmountStr;
    }

    public void setMinBidAmountStr(String minBidAmountStr) {
        this.minBidAmountStr = minBidAmountStr;
    }

    public String getProductAmountStr() {
        return productAmountStr;
    }

    public void setProductAmountStr(String productAmountStr) {
        this.productAmountStr = productAmountStr;
    }

    public String getRestBidAmountStr() {
        return restBidAmountStr;
    }

    public void setRestBidAmountStr(String restBidAmountStr) {
        this.restBidAmountStr = restBidAmountStr;
    }

    public Long getBorrowUserPhone() {
        return borrowUserPhone;
    }

    public void setBorrowUserPhone(Long borrowUserPhone) {
        this.borrowUserPhone = borrowUserPhone;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }


    public String getAddProfitStr() {
        return addProfitStr;
    }

    public void setAddProfitStr(String addProfitStr) {
        this.addProfitStr = addProfitStr;
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

    public String getGuaranteeName() {
        return guaranteeName;
    }

    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }

    public String getRepurchaseModeDesc() {
        return repurchaseModeDesc;
    }

    public void setRepurchaseModeDesc(String repurchaseModeDesc) {
        this.repurchaseModeDesc = repurchaseModeDesc;
    }

    public Long getInvestEndDateTime() {
        return investEndDateTime;
    }

    public void setInvestEndDateTime(Long investEndDateTime) {
        this.investEndDateTime = investEndDateTime;
    }

    public String getOriBorrowerIds() {
        return oriBorrowerIds;
    }

    public void setOriBorrowerIds(String oriBorrowerIds) {
        this.oriBorrowerIds = oriBorrowerIds;
    }

    public String getOriBorrowers() {
        return oriBorrowers;
    }

    public void setOriBorrowers(String oriBorrowers) {
        this.oriBorrowers = oriBorrowers;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getAssetTypeMiniName() {
        return assetTypeMiniName;
    }

    public void setAssetTypeMiniName(String assetTypeMiniName) {
        this.assetTypeMiniName = assetTypeMiniName;
    }

    public String getRateOfDiscount() {
        return rateOfDiscount;
    }

    public void setRateOfDiscount(String rateOfDiscount) {
        this.rateOfDiscount = rateOfDiscount;
    }


    public String getTransferTotalAmountStr() {
        return transferTotalAmountStr;
    }

    public void setTransferTotalAmountStr(String transferTotalAmountStr) {
        this.transferTotalAmountStr = transferTotalAmountStr;
    }


    public String getRecentRepaymentDateStr() {
        return recentRepaymentDateStr;
    }

    public void setRecentRepaymentDateStr(String recentRepaymentDateStr) {
        this.recentRepaymentDateStr = recentRepaymentDateStr;
    }

    public String getExpectProfitStr() {
        return expectProfitStr;
    }

    public void setExpectProfitStr(String expectProfitStr) {
        this.expectProfitStr = expectProfitStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Long getOldProductId() {
        return oldProductId;
    }

    public void setOldProductId(Long oldProductId) {
        this.oldProductId = oldProductId;
    }

    public BigDecimal getTransferTotalAmount() {
        return transferTotalAmount;
    }

    public void setTransferTotalAmount(BigDecimal transferTotalAmount) {
        this.transferTotalAmount = transferTotalAmount;
    }

    public String getBorrowUserRealName() {
        return borrowUserRealName;
    }

    public void setBorrowUserRealName(String borrowUserRealName) {
        this.borrowUserRealName = borrowUserRealName;
    }

    public String getProductStatusDesc() {
        return productStatusDesc;
    }

    public void setProductStatusDesc(String productStatusDesc) {
        this.productStatusDesc = productStatusDesc;
    }

    public Integer getProductStatusSearchType() {
        return productStatusSearchType;
    }

    public void setProductStatusSearchType(Integer productStatusSearchType) {
        this.productStatusSearchType = productStatusSearchType;
    }

    public Integer getAmountSearchType() {
        return amountSearchType;
    }

    public void setAmountSearchType(Integer amountSearchType) {
        this.amountSearchType = amountSearchType;
    }

    public Integer getProductDeadlineSearchType() {
        return productDeadlineSearchType;
    }

    public void setProductDeadlineSearchType(Integer productDeadlineSearchType) {
        this.productDeadlineSearchType = productDeadlineSearchType;
    }

    public Integer getProfitSearchType() {
        return profitSearchType;
    }

    public void setProfitSearchType(Integer profitSearchType) {
        this.profitSearchType = profitSearchType;
    }

    public BigDecimal getProductAmountStart() {
        return productAmountStart;
    }

    public void setProductAmountStart(BigDecimal productAmountStart) {
        this.productAmountStart = productAmountStart;
    }

    public BigDecimal getProductAmountEnd() {
        return productAmountEnd;
    }

    public void setProductAmountEnd(BigDecimal productAmountEnd) {
        this.productAmountEnd = productAmountEnd;
    }

    public Integer getProductDeadlineStart() {
        return productDeadlineStart;
    }

    public void setProductDeadlineStart(Integer productDeadlineStart) {
        this.productDeadlineStart = productDeadlineStart;
    }

    public Integer getProductDeadlineEnd() {
        return ProductDeadlineEnd;
    }

    public void setProductDeadlineEnd(Integer productDeadlineEnd) {
        ProductDeadlineEnd = productDeadlineEnd;
    }

    public BigDecimal getProfitStart() {
        return profitStart;
    }

    public void setProfitStart(BigDecimal profitStart) {
        this.profitStart = profitStart;
    }

    public BigDecimal getProfitEnd() {
        return profitEnd;
    }

    public void setProfitEnd(BigDecimal profitEnd) {
        this.profitEnd = profitEnd;
    }

    public String getBidPercent() {
        return bidPercent;
    }

    public void setBidPercent(String bidPercent) {
        this.bidPercent = bidPercent;
    }

    public Date getFirstCheckTime() {
        return firstCheckTime;
    }

    public void setFirstCheckTime(Date firstCheckTime) {
        this.firstCheckTime = firstCheckTime;
    }

    public Date getFinalCheckTime() {
        return finalCheckTime;
    }

    public void setFinalCheckTime(Date finalCheckTime) {
        this.finalCheckTime = finalCheckTime;
    }

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

    public String getProductStatusStr() {
        return productStatusStr;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr = productStatusStr;
    }

    @Override
    public Long getSmartBidPlanId() {
        return smartBidPlanId;
    }

    @Override
    public void setSmartBidPlanId(Long smartBidPlanId) {
        this.smartBidPlanId = smartBidPlanId;
    }

    public String getSmartName() {
        return smartName;
    }

    public void setSmartName(String smartName) {
        this.smartName = smartName;
    }
}
