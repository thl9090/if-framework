package com.yx.user.model;

import java.io.Serializable;

/**
 * 客户端信息
 *
 * @author YanBingHao
 * @since 2018/11/27
 */
public class ClientInfo implements Serializable {

    private static final long serialVersionUID = -5150306242051715912L;

    private String accessToken;
    private Integer channelType;
    private String channelVersion;
    private String derviceName;
    private String ip;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getChannelVersion() {
        return channelVersion;
    }

    public void setChannelVersion(String channelVersion) {
        this.channelVersion = channelVersion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getDerviceName() {
        return derviceName;
    }

    public void setDerviceName(String derviceName) {
        this.derviceName = derviceName;
    }
}
