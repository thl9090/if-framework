package com.yx.api.server.controller;

import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.StringUtils;
import com.yx.user.model.UserInfo;
import com.yx.user.rpc.api.UserInfoService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 控制器基类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
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
        String userId = request.getAttribute(AuthorizationConstant.USER_ID).toString();
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
        String userIdStr = request.getAttribute(AuthorizationConstant.USER_ID).toString();
        if (StringUtils.isBlank(userIdStr)) {
            return null;
        }

        Long userId = Long.valueOf(userIdStr);
        if (userId == null) {
            throw new BusinessException(Constants.ResultCodeEnum.UNAUTHORIZED.value(), "未获取到用户id");
        }
//        	UserInfo userInfo = (UserInfo)CacheUtil.getCache().get(Constants.APP_CURRENT_USER+userId);
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
        //清空用户之前的token
        Object o = CacheUtil.getCache().get(Constants.CURRENT_USER_TOKEN + userInfo.getId());
        if (o != null) {
            String oldJwt = (String) o;
            if (StringUtils.isNotBlank(oldJwt)) {
                CacheUtil.getCache().del(oldJwt);
            }
        }
        //将用户id和token关联
        CacheUtil.getCache().set(Constants.CURRENT_USER_TOKEN + userInfo.getId(), jwt);
        //将jwt作为key，和用户信息关联
        CacheUtil.getCache().set(jwt, userInfo, AuthorizationConstant.APP_AUTH_EXPIRE_TIME.intValue());

    }


}
