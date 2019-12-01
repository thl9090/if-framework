package com.yx.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户的登陆日志
 */

public class UserLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    private String tableName;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 版本号
     */
    private String channelVersion;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 设备名称
     */
    private String derviceName;

    /**
     * 登陆时间
     */
    private Date loginTime;

    private Date createTimeStart;
    private Date createTimeEnd;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getChannelVersion() {
        return channelVersion;
    }

    public void setChannelVersion(String channelVersion) {
        this.channelVersion = channelVersion;
    }

    public String getDerviceName() {
        return derviceName;
    }

    public void setDerviceName(String derviceName) {
        this.derviceName = derviceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
}
