package com.yx.sys.model.product;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 标的信息表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-30
 */
@TableName("product")
public class Product extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 标的编号(原标的id)
     */
    @ApiModelProperty(value = "标的编号")
    @TableField("product_no")
    private String productNo;
    /**
     * 标的名称
     */
    @ApiModelProperty(value = "标的名称")
    @TableField("title")
    private String title;
    /**
     * 受托人id
     */
    @TableField("borrow_user_id")
    private Long borrowUserId;
    /**
     * 借款类型：3、安享系列；4、私享系列；5、尊享系列；6、福享系列；8、新手福利；10、净值标
     */
    @ApiModelProperty(value = "借款类型：3、安享系列；4、私享系列；5、尊享系列；6、福享系列；8、新手福利；10、净值标")
    @TableField("asset_type")
    private Integer assetType;

    /**
     * 保障机构
     */
    @TableField("guarantee_id")
    private Long guaranteeId;

    /**
     * 借款用途：1、个人消费；2、短息周转；3、企业资金周转；5、赎楼；6、企业过桥；4、其他
     */
    @ApiModelProperty(value = "借款用途：1、个人消费；2、短息周转；3、企业资金周转；5、赎楼；6、企业过桥；4、其他")
    @TableField("use_type")
    private Integer useType;


    /**
     * 借款用途备注: 当借款用途类型为其它的时候，借款用途为备注内容
     */
    @ApiModelProperty(value = "借款用途备注")
    @TableField("use_type_remark")
    private String useTypeRemark;

    /**
     * 是否参与活动：1、是；0、否
     */
    @ApiModelProperty(value = "是否参与活动：1、是；0、否")
    @TableField("is_activity")
    private Integer isActivity;
    /**
     * 是否为企业借款：1、是；0、否
     */
    @ApiModelProperty(value = "是否为企业借款：1、是；0、否")
    @TableField("is_company")
    private Integer isCompany;
    /**
     * 还款方式：1、按月等额本息；2、按月先息后本；3、按日计息,到期还本息
     */
    @ApiModelProperty(value = "还款方式：1、按月等额本息；2、按月先息后本；3、按日计息,到期还本息")
    @TableField("repurchase_mode")
    private Integer repurchaseMode;
    /**
     * 是否可转让：1、是；0、否；
     */
    @ApiModelProperty(value = "是否可转让：1、是；0、否；")
    @TableField("is_transfer")
    private Integer isTransfer;
    /**
     * 借款金额
     */
    @ApiModelProperty(value = "借款金额")
    @TableField("product_amount")
    private BigDecimal productAmount;
    /**
     * 借款期限
     */
    @ApiModelProperty(value = "借款期限")
    @TableField("product_deadline")
    private Integer productDeadline;

    /**
     * 年收益
     */
    @ApiModelProperty(value = "年收益")
    @TableField("profit")
    private BigDecimal profit;
    /**
     * 借款人服务费率
     */
    @ApiModelProperty(value = "借款人服务费率")
    @TableField("service_fee")
    private BigDecimal serviceFee;
    /**
     * 截标金额
     */
    @ApiModelProperty(value = "截标金额")
    @TableField("stop_bid_amount")
    private BigDecimal stopBidAmount;
    /**
     * 截标时间
     */
    @ApiModelProperty(value = "截标时间")
    @TableField("stop_bid_time")
    private Date stopBidTime;
    /**
     * 投资人服务费率
     */
    @ApiModelProperty(value = "投资人服务费率")
    @TableField("bid_server_fee")
    private BigDecimal bidServerFee;
    /**
     * 招标天数
     */
    @ApiModelProperty(value = "招标天数")
    @TableField("time_count")
    private Integer timeCount;
    /**
     * 最小投标金额
     */
    @ApiModelProperty(value = "最小投资金额")
    @TableField("min_bid_amount")
    private BigDecimal minBidAmount;
    /**
     * 最大投标金额
     */
    @ApiModelProperty(value = "最大投资金额")
    @TableField("max_bid_amount")
    private BigDecimal maxBidAmount;
    /**
     * 借款描述
     */
    @ApiModelProperty(value = "借款描述")
    @TableField("borrow_desc")
    private String borrowDesc;
    /**
     * 反担保
     */
    @ApiModelProperty(value = "反担保")
    @TableField("guarantee_desc")
    private String guaranteeDesc;
    /**
     * 风控描述
     */
    @ApiModelProperty(value = "风控描述")
    @TableField("risk_desc")
    private String riskDesc;
    /**
     * 标地状态
     */
    @ApiModelProperty(value = "标地状态：2、初审；3、终审；4、初审拒绝；5、终审拒绝；6、招标失败；7、招标中；8、已撤销；9、流标；10、还款中；11、已还清；12、逾期；14、满标；20、放款中；21、放款失败")
    @TableField("product_status")
    private Integer productStatus;
    /**
     * 标的类型
     */
    @ApiModelProperty(value = "标地类型：1、债转；2、借款；3、商业保理；4、融资租赁")
    @TableField("product_type")
    private Integer productType;

    /**
     * 投标金额
     */
    @ApiModelProperty(value = "投标金额")
    @TableField("bid_amount")
    private BigDecimal bidAmount;

    /**
     * 招标开始时间
     */
    @ApiModelProperty(value = "招标开始时间")
    @TableField("invest_begin_date")
    private Date investBeginDate;

    /**
     * 招标结束时间
     */
    @ApiModelProperty(value = "招标结束时间")
    @TableField("invest_end_date")
    private Date investEndDate;

    /**
     * 分债权首债权id
     */
    @TableField("root_product_id")
    private Long rootProductId;

    @ApiModelProperty(value = "满标时间")
    @TableField("full_bid_date")
    private Date fullBidDate;


    /**
     * 流水号
     */
    @TableField("channel_flow")
    private String channelFlow;

    @ApiModelProperty(value = "是否加息，1：是；2：否；")
    @TableField("is_add_profit")
    private Integer isAddProfit;

    @ApiModelProperty(value = "加息出资方，1：借款人；2：平台；")
    @TableField("add_profit_type")
    private Integer addProfitType;

    /**
     * 加息
     */
    @ApiModelProperty(value = "加息")
    @TableField("add_profit")
    private BigDecimal addProfit;

    @ApiModelProperty(value = "是否自动放款：0、否、1、是")
    @TableField("auto_loan")
    private Integer autoLoan;

    @ApiModelProperty(value = "是否删除：0、否、1、是")
    @TableField("is_del")
    private Integer isDel;

    @ApiModelProperty(value = "标的评级")
    @TableField("product_rating")
    private Integer productRating;

    @ApiModelProperty(value = "首页优先展示：0、否、1、是")
    @TableField("index_show")
    private Integer indexShow;

    @ApiModelProperty(value = "请求银行发标接口  银行返回标的编号")
    @TableField("subject_no")
    private String subjectNo;


    @ApiModelProperty(value = "推荐方平台注册 id（垫付账户id）")
    @TableField("advance_payment_user_id")
    private Long advancePaymentUserId;

    @ApiModelProperty(value = "是否新系统数据：1、是；0、老系统数据")
    @TableField("is_new_data")
    private Integer isNewData;

    @ApiModelProperty(value = "标的来源：1、首台手动添加；2、流标产生；3、债权转让产生")
    @TableField("source")
    private Integer source;

    @ApiModelProperty(value = "是否加入智投：1、是；0、否")
    @TableField("is_join_smart")
    private Integer isJoinSmart;

    @ApiModelProperty(value = "智投id")
    @TableField("smart_bid_plan_id")
    private Long smartBidPlanId;



    public Long getAdvancePaymentUserId() {
        return advancePaymentUserId;
    }

    public void setAdvancePaymentUserId(Long advancePaymentUserId) {
        this.advancePaymentUserId = advancePaymentUserId;
    }

    public String getSubjectNo() {
        return subjectNo;
    }

    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo;
    }

    public Integer getIsAddProfit() {
        return isAddProfit;
    }

    public void setIsAddProfit(Integer isAddProfit) {
        this.isAddProfit = isAddProfit;
    }

    public Integer getAddProfitType() {
        return addProfitType;
    }

    public void setAddProfitType(Integer addProfitType) {
        this.addProfitType = addProfitType;
    }

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

    public Long getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(Long borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Integer getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(Integer isActivity) {
        this.isActivity = isActivity;
    }

    public Integer getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(Integer isCompany) {
        this.isCompany = isCompany;
    }

    public Integer getRepurchaseMode() {
        return repurchaseMode;
    }

    public void setRepurchaseMode(Integer repurchaseMode) {
        this.repurchaseMode = repurchaseMode;
    }

    public Integer getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(Integer isTransfer) {
        this.isTransfer = isTransfer;
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


    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Long getRootProductId() {
        return rootProductId;
    }

    public void setRootProductId(Long rootProductId) {
        this.rootProductId = rootProductId;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
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

    public Long getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(Long guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public String getChannelFlow() {
        return channelFlow;
    }

    public void setChannelFlow(String channelFlow) {
        this.channelFlow = channelFlow;
    }

    public BigDecimal getAddProfit() {
        return addProfit;
    }

    public void setAddProfit(BigDecimal addProfit) {
        this.addProfit = addProfit;
    }

    public Date getFullBidDate() {
        return fullBidDate;
    }

    public void setFullBidDate(Date fullBidDate) {
        this.fullBidDate = fullBidDate;
    }

    public Integer getAutoLoan() {
        return autoLoan;
    }

    public void setAutoLoan(Integer autoLoan) {
        this.autoLoan = autoLoan;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIndexShow() {
        return indexShow;
    }

    public void setIndexShow(Integer indexShow) {
        this.indexShow = indexShow;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    public String getUseTypeRemark() {
        return useTypeRemark;
    }

    public void setUseTypeRemark(String useTypeRemark) {
        this.useTypeRemark = useTypeRemark;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNo='" + productNo + '\'' +
                ", title='" + title + '\'' +
                ", borrowUserId=" + borrowUserId +
                ", assetType=" + assetType +
                ", guaranteeId=" + guaranteeId +
                ", useType=" + useType +
                ", useTypeRemark='" + useTypeRemark + '\'' +
                ", isActivity=" + isActivity +
                ", isCompany=" + isCompany +
                ", repurchaseMode=" + repurchaseMode +
                ", isTransfer=" + isTransfer +
                ", productAmount=" + productAmount +
                ", productDeadline=" + productDeadline +
                ", profit=" + profit +
                ", serviceFee=" + serviceFee +
                ", stopBidAmount=" + stopBidAmount +
                ", stopBidTime=" + stopBidTime +
                ", bidServerFee=" + bidServerFee +
                ", timeCount=" + timeCount +
                ", minBidAmount=" + minBidAmount +
                ", maxBidAmount=" + maxBidAmount +
                ", borrowDesc='" + borrowDesc + '\'' +
                ", guaranteeDesc='" + guaranteeDesc + '\'' +
                ", riskDesc='" + riskDesc + '\'' +
                ", productStatus=" + productStatus +
                ", productType=" + productType +
                ", bidAmount=" + bidAmount +
                ", investBeginDate=" + investBeginDate +
                ", investEndDate=" + investEndDate +
                ", rootProductId=" + rootProductId +
                ", fullBidDate=" + fullBidDate +
                ", channelFlow='" + channelFlow + '\'' +
                ", isAddProfit=" + isAddProfit +
                ", addProfitType=" + addProfitType +
                ", addProfit=" + addProfit +
                ", autoLoan=" + autoLoan +
                ", isDel=" + isDel +
                ", productRating=" + productRating +
                ", indexShow=" + indexShow +
                ", subjectNo='" + subjectNo + '\'' +
                ", advancePaymentUserId=" + advancePaymentUserId +
                ", isNewData=" + isNewData +
                ", source=" + source +
                ", isJoinSmart=" + isJoinSmart +
                ", smartBidPlanId=" + smartBidPlanId +
                '}';
    }

    public Integer getIsNewData() {
        return isNewData;
    }

    public void setIsNewData(Integer isNewData) {
        this.isNewData = isNewData;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getIsJoinSmart() {
        return isJoinSmart;
    }

    public void setIsJoinSmart(Integer isJoinSmart) {
        this.isJoinSmart = isJoinSmart;
    }

    public Long getSmartBidPlanId() {
        return smartBidPlanId;
    }

    public void setSmartBidPlanId(Long smartBidPlanId) {
        this.smartBidPlanId = smartBidPlanId;
    }
}
