package com.yx.user.model.referrer;

import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @ClassName 推荐
 * @Author zhangxiaowen
 * @Date 2018/8/17 17:59
 * @Version 1.0
 **/
@ApiModel(value = "我的推荐")
public class UserReferrerModel extends BaseModel {

    private static final long serialVersionUID = 3943261592653245622L;

    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    private Long userId;
    private Integer currentPage = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "推荐人类型: 1 投资人 2 借款人")
    private Integer type;

    @ApiModelProperty(value = "推荐人id")
    private Long refUserId;

    @ApiModelProperty(value = "推荐人名称")
    private String refName;

    @ApiModelProperty(value = "推荐人真实姓名")
    private String refRealName;

    @ApiModelProperty(value = "推荐人电话号码")
    private String refPhone;

    @ApiModelProperty(value = "推荐人注册时间")
    private String refCreateTime;

    @ApiModelProperty(value = "推荐人身份证号码")
    private String refIdNum;

    @ApiModelProperty(value = "推荐人投标标题 推荐人借款标标题")
    private String titles;

    @ApiModelProperty(value = "推荐人投的标id 推荐人借款标id")
    private String productId;

    @ApiModelProperty(value = "推荐人投标id")
    private String bidOrderId;

    @ApiModelProperty(value = "推荐人投资金额  推荐人借款金额")
    private String refBidAmount;

    @ApiModelProperty(value = "投标期限 借款标期限")
    private String biddeadline;

    @ApiModelProperty(value = "投标利率 借款标利率")
    private String profit;

    @ApiModelProperty(value = "还款方式")
    private String repurchase;

    @ApiModelProperty(value = "投资时间  借款时间")
    private String  investmentTime;

    @ApiModelProperty(value = "推荐投资人集合")
    private List<Long> refUserList;

    @ApiModelProperty(value = "标的放款时间")
    private String loanTime;

    @ApiModelProperty(value = "标的类型")
    private String productType;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getBidOrderId() {
        return bidOrderId;
    }

    public void setBidOrderId(String bidOrderId) {
        this.bidOrderId = bidOrderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRefPhone() {
        return refPhone;
    }

    public void setRefPhone(String refPhone) {
        this.refPhone = refPhone;
    }

    public String getRefRealName() {
        return refRealName;
    }

    public void setRefRealName(String refRealName) {
        this.refRealName = refRealName;
    }

    public List<Long> getRefUserList() {
        return refUserList;
    }

    public void setRefUserList(List<Long> refUserList) {
        this.refUserList = refUserList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRefIdNum() {
        return refIdNum;
    }

    public void setRefIdNum(String refIdNum) {
        this.refIdNum = refIdNum;
    }

    public String getBiddeadline() {
        return biddeadline;
    }

    public void setBiddeadline(String biddeadline) {
        this.biddeadline = biddeadline;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getRepurchase() {
        return repurchase;
    }

    public void setRepurchase(String repurchase) {
        this.repurchase = repurchase;
    }

    public String getInvestmentTime() {
        return investmentTime;
    }

    public void setInvestmentTime(String investmentTime) {
        this.investmentTime = investmentTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getRefUserId() {
        return refUserId;
    }

    public void setRefUserId(Long refUserId) {
        this.refUserId = refUserId;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefBidAmount() {
        return refBidAmount;
    }

    public void setRefBidAmount(String refBidAmount) {
        this.refBidAmount = refBidAmount;
    }

    public String getRefCreateTime() {
        return refCreateTime;
    }

    public void setRefCreateTime(String refCreateTime) {
        this.refCreateTime = refCreateTime;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }
}
