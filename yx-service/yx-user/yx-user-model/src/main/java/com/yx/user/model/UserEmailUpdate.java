package com.yx.user.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UserEmailUpdate implements Serializable {

    private static final long serialVersionUID = 6155209772205328728L;

    @ApiModelProperty(value = "地址中获取参数")
    private String paramKey;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * email
     */
    private String email;

    private String accessToken;

    private String uuid;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
