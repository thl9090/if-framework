package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *      投标列表
 * </p>
 *
 * @author zhangPanPan
 * @since 2018/8/21 0021
 */
public class BidOrderExp implements Serializable {

    @Excel(name = "借款编号")
    private Long productId;
    @Excel(name = "借款标题")
    private String title;
    @Excel(name = "投标时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Excel(name = "投资金额")
    private BigDecimal capital;
    @Excel(name = "利息")
    private BigDecimal bidServerFee;
    @Excel(name = "投资人")
    private String userName;
    @Excel(name = "投资人姓名")
    private String realName;
    @Excel(name = "推荐人")
    private String referrerRealName;
    @Excel(name = "手机号码")
    private Long phone;
    @Excel(name = "投标状态")
    private String orderStatus;
    @Excel(name = "借款状态")
    private String assetType;
    @Excel(name = "借款类型")
    private String productStatus;
    @Excel(name = "计息时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date fullBidDate;
    @Excel(name = "借款期限")
    private Integer productDeadline;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getBidServerFee() {
        return bidServerFee;
    }

    public void setBidServerFee(BigDecimal bidServerFee) {
        this.bidServerFee = bidServerFee;
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

    public String getReferrerRealName() {
        return referrerRealName;
    }

    public void setReferrerRealName(String referrerRealName) {
        this.referrerRealName = referrerRealName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public Date getFullBidDate() {
        return fullBidDate;
    }

    public void setFullBidDate(Date fullBidDate) {
        this.fullBidDate = fullBidDate;
    }

    public Integer getProductDeadline() {
        return productDeadline;
    }

    public void setProductDeadline(Integer productDeadline) {
        this.productDeadline = productDeadline;
    }
}
