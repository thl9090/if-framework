package com.yx.sys.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *      用户个人流水的信息导出实体类
 * </p>
 *
 * @author zhangPanPan
 * @since 2018/8/17 0017
 */
public class UserFlowExp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "流水号")
    private String channelFlow;

    @Excel(name = "用户名")
    private String userName;

    @Excel(name = "真实姓名")
    private String realName;

    @Excel(name = "手机号码")
    private Long phone;

    @Excel(name = "证件号码")
    private String idNumber;

    @Excel(name = "银行账号")
    private String acno;

    @Excel(name = "类型")
    private String flowTypeName;

    @Excel(name = "金额")
    private BigDecimal amount;

    @Excel(name = "状态")
    private String statusName;

    @Excel(name = "收支状态")
    private String transTypeName;

    @Excel(name = "创建时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
