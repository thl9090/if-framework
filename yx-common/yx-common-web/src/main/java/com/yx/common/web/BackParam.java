package com.yx.common.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 获取银行回跳参数信息
 *
 * @author YanBingHao
 * @since 2018/11/27
 */
@ApiModel(value = "获取银行回跳参数信息")
public class BackParam implements Serializable {

    private static final long serialVersionUID = 6155209772205328728L;

    @ApiModelProperty(value = "从银行回跳地址中获取参数")
    private String paramKey;

    @ApiModelProperty(value = "交易编码")
    private String tradeCode;

    @ApiModelProperty(value = "渠道类型；1：PC；2：安卓；3：IOS；4：WX；")
    private Integer channelType;

    @ApiModelProperty(value = "渠道流水号")
    private String channelFlow;

    @ApiModelProperty(value = "登录token")
    private String accessToken;

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getChannelFlow() {
        return channelFlow;
    }

    public void setChannelFlow(String channelFlow) {
        this.channelFlow = channelFlow;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
