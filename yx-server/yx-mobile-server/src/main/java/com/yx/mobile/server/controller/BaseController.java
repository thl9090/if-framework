package com.yx.mobile.server.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.redis.util.CacheUtil;
import com.yx.user.model.ClientInfo;
import com.yx.user.model.UserInfo;
import com.yx.user.rpc.api.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 控制器基类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
public abstract class BaseController {


    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取当前用户Id
     *
     * @return Long
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    protected Long getCurrentUserId(HttpServletRequest request) {
        Object userIdObj = request.getAttribute(AuthorizationConstant.USER_ID);
        if (userIdObj == null) {
            return null;
        }
        String userId = userIdObj.toString();
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return Long.valueOf(userId);
    }

    /**
     * 获取当前用户
     *
     * @return Object
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    protected UserInfo getCurrentUser(HttpServletRequest request) throws BusinessException {
        Long userId = this.getCurrentUserId(request);
        if (userId == null) {
            throw new BusinessException(Constants.ResultCodeEnum.UNAUTHORIZED.value(), "未获取到用户id");
        }
        //UserInfo userInfo = (UserInfo)CacheUtil.getCache().get(Constants.APP_CURRENT_USER+userId);
        UserInfo userInfo = null;
        if (userInfo == null) {
            userInfo = userInfoService.selectById(userId);
            //用户信息放入缓存
            //CacheUtil.getCache().set(Constants.APP_CURRENT_USER+userId, userInfo, 5*60);
        }
        if (userInfo == null) {
            throw new BusinessException(Constants.ResultCodeEnum.UNAUTHORIZED.value(), "未获取到用户信息");
        }
        return userInfo;
    }

    /**
     * 更新用户token信息到redis中
     *
     * @param userInfo
     * @param jwt
     */
    protected void updateCurrentUser(UserInfo userInfo, String jwt) throws BusinessException {
        if (AuthorizationConstant.APP_SSO) {
            //清空用户之前的token
            Object o = CacheUtil.getCache().get(Constants.CURRENT_USER_TOKEN + userInfo.getId());
            if (o != null) {
                String oldJwt = (String) o;
                //保证同一用户只能同时在一台设备上使用
                if (StringUtils.isNotBlank(oldJwt)) {
                    CacheUtil.getCache().del(oldJwt);
                }
            }
        }

        //将用户id和token关联
        CacheUtil.getCache().set(Constants.CURRENT_USER_TOKEN + userInfo.getId(), jwt);
        //将jwt作为key，和用户信息关联
        CacheUtil.getCache().set(jwt, userInfo, AuthorizationConstant.APP_AUTH_EXPIRE_TIME.intValue());

    }


    protected ClientInfo getClientInfo(HttpServletRequest request) {
        String accessToken = request.getHeader(AuthorizationConstant.ACCESS_TOKEN_HEADER);
        String channelType = request.getHeader(AuthorizationConstant.CHANNEL_TYPE_HEADER);
        String channelVersion = request.getHeader(AuthorizationConstant.CHANNEL_VERSION_HEADER);
        String derviceName = request.getHeader(AuthorizationConstant.DERVICE_NAME_HEADER);


        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setAccessToken(accessToken);
        if (StringUtils.isNotBlank(channelType)) {
            clientInfo.setChannelType(Integer.valueOf(channelType));
        }
        if (StringUtils.isNotBlank(channelVersion)) {
            clientInfo.setChannelVersion(channelVersion);
        }
        if (StringUtils.isNotBlank(derviceName)) {
            clientInfo.setDerviceName(derviceName);
        }
        String ip = null;
        try {
            ip = HttpUtil.getClientIP(request);
        } catch (Exception e) {
        }
        clientInfo.setIp(ip);

        log.info(String.format("客户端信息：【%s】", JSONObject.toJSONString(clientInfo)));
        return clientInfo;
    }


}
